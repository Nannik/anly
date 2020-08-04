package ua.com.anly.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.anly.entity.Order;
import ua.com.anly.entity.Product;
import ua.com.anly.entity.User;
import ua.com.anly.repository.CategoryRepo;
import ua.com.anly.repository.OrderRepo;
import ua.com.anly.service.MailSender;
import ua.com.anly.service.UserService;

import javax.mail.MessagingException;
import java.util.List;

@Controller
public class OrderController {
    private final MainController mainController;
    private final OrderRepo orderRepo;
    private final CategoryRepo categoryRepo;
    private final UserService userService;
    private final MailSender mailSender;
    private final SessionRegistry sessionRegistry;

    public OrderController(MainController mainController, OrderRepo orderRepo, CategoryRepo categoryRepo, UserService userService, MailSender mailSender, SessionRegistry sessionRegistry) {
        this.mainController = mainController;
        this.orderRepo = orderRepo;
        this.categoryRepo = categoryRepo;
        this.userService = userService;
        this.mailSender = mailSender;
        this.sessionRegistry = sessionRegistry;
    }

    @GetMapping("/order")
    @PreAuthorize("isAuthenticated()")
    public String fillOrder(Model model, @AuthenticationPrincipal User user) {
        if (user.getCard().size() == 0) {
            model.addAttribute("message", "Card is empty");
            return mainController.main(model);
        }

        model.addAttribute("categories", categoryRepo.findAll());

        return "order";
    }

    @PostMapping("/order")
    @PreAuthorize("isAuthenticated()")
    public String createOrder(@RequestParam(required = false) String phoneNumber, @RequestParam String deliveryType, @RequestParam(defaultValue = "Address is not present") String address, @AuthenticationPrincipal User user, Model model) {
        int orderNumber = getUniqueOrderNumber(10000, 99999);
        int price = 0;

        for (Product product : user.getCard()) {
            price += product.getPrice();
        }

        Order order = new Order(user, orderNumber, deliveryType, price);
        orderRepo.save(order);

        fillProductsToOrder(order, user.getCard());

        userService.removeAllFromCard(user);
        userService.addOrder(user, order);


        String subject = "Order created";
        String message = String.format(
                "Order %s created", orderNumber
        );

        try {
            mailSender.send(user.getEmail(), subject, message);
        } catch (MessagingException e) {
            model.addAttribute("message", "Не удалось отправить письмо на почту. Номер заказа: " + orderNumber);
            return mainController.main(model);
        }

        return "redirect:/";
    }

    private void fillProductsToOrder(Order order, List<Product> products) {
        for (Product product : products) {
            order.getProducts().add(product);
            orderRepo.save(order);
        }
    }

    private int getUniqueOrderNumber(int min, int max) {
        Iterable<Order> orders = orderRepo.findAll();

        int num = 10000;
        boolean isUnique = false;
        while (!isUnique) {
            isUnique = true;

            num = min + (int)(Math.random() * ((max - min) + 1));

            for(Order order : orders) {
                if (order.getOrderNumber() == num) {
                    isUnique = false;
                    break;
                }
            }
        }

        return num;
    }

    @PostMapping("/changeOrderStatus")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String changeStatus(@RequestParam int id, @RequestParam int status) {
        Order order = orderRepo.findById(id);
        order.setStatus(status);

        orderRepo.save(order);

        for (Object obj : sessionRegistry.getAllPrincipals()) {
            User user = (User) obj;

            if (user.getId() == order.getUser().getId()) {
                user.getOrders().add(order);
            }
        }

        return "redirect:/admin";
    }
}
