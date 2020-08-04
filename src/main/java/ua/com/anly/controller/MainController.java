package ua.com.anly.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.anly.entity.User;
import ua.com.anly.repository.CategoryRepo;
import ua.com.anly.repository.OrderRepo;
import ua.com.anly.repository.ProductRepo;
import ua.com.anly.repository.UserRepo;
import ua.com.anly.service.MailSender;

import javax.mail.MessagingException;

@Controller
public class MainController {
    private final CategoryRepo categoryRepo;
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;
    private final MailSender mailSender;

    public MainController(CategoryRepo categoryRepo, ProductRepo productRepo, OrderRepo orderRepo, UserRepo userRepo, MailSender mailSender) {
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.mailSender = mailSender;
    }

    @GetMapping
    public String main(Model model) {
        model.addAttribute("categories", categoryRepo.findAll());

        model.addAttribute("products", productRepo.findAll());

        return "main";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("categories", categoryRepo.findAll());

        return "login";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String adminPage(Model model) {
        model.addAttribute("categories", categoryRepo.findAll());

        model.addAttribute("orders", orderRepo.findAll());

        return "admin";
    }

    @PostMapping("/mailAll")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String mailAll(@RequestParam String subject, @RequestParam String text, Model model) {
        Iterable<User> users = userRepo.findAll();

        for (User user : users) {
            try {
                mailSender.send(user.getEmail(), subject, text);
            } catch (MessagingException e) {
                model.addAttribute("message", "Can't sent message");
                return main(model);
            }
        }

        return "redirect:/";
    }
}
