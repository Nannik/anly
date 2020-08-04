package ua.com.anly.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.anly.entity.Product;
import ua.com.anly.entity.User;
import ua.com.anly.entity.message.ChildMessage;
import ua.com.anly.entity.message.ParentMessage;
import ua.com.anly.repository.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/product")
public class ProductController {
    private final CategoryRepo categoryRepo;
    private final ProductRepo productRepo;
    private final ParentMessageRepo parentMessageRepo;
    private final ChildMessageRepo childMessageRepo;
    private final UserRepo userRepo;
    private final MainController mainController;
    private final SessionRegistry sessionRegistry;

    ProductController(CategoryRepo categoryRepo, ProductRepo productRepo, ParentMessageRepo parentMessageRepo, ChildMessageRepo childMessageRepo, UserRepo userRepo, MainController mainController, SessionRegistry sessionRegistry) {
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
        this.parentMessageRepo = parentMessageRepo;
        this.childMessageRepo = childMessageRepo;
        this.userRepo = userRepo;
        this.mainController = mainController;
        this.sessionRegistry = sessionRegistry;
    }

    @GetMapping
    public String main(Model model) {
        return mainController.main(model);
    }

    @GetMapping("/{id}")
    public String view(@PathVariable String id, @RequestParam(name = "edit", defaultValue = "false") boolean isEdit, Model model) {
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("isEdit", isEdit);

        Product product = productRepo.findById(Integer.parseInt(id));
        model.addAttribute(product);

        model.addAttribute("messages", parentMessageRepo.findAllByProductId(Integer.parseInt(id)));

        return "product";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String add(Model model) {
        model.addAttribute("categories", categoryRepo.findAll());

        return "product";
    }

    @PostMapping("/addMessage")
    @PreAuthorize("hasAuthority('USER')")
    public String addMessage(@RequestParam String text, @RequestParam byte rate, @RequestParam int productId, Authentication authentication) {
        User user = userRepo.findByName(authentication.getName());
        Product product = productRepo.findById(productId);
        ParentMessage message = new ParentMessage(user, text, rate, product);

        List<ParentMessage> messages = (List<ParentMessage>) parentMessageRepo.findAll();
        messages.add(message);

        parentMessageRepo.saveAll(messages);

        return "redirect:/product/" + productId;
    }

    @PostMapping("/addChildMessage")
    @PreAuthorize("hasAuthority('USER')")
    public String addChildMessage(@RequestParam String text, @RequestParam int parentMessageId, Authentication authentication) {
        User user = userRepo.findByName(authentication.getName());

        ParentMessage parentMessage = parentMessageRepo.findById(parentMessageId);
        ChildMessage message = new ChildMessage(user, text, parentMessage);

        List<ChildMessage> messages = (List<ChildMessage>) childMessageRepo.findAll();
        messages.add(message);

        childMessageRepo.saveAll(messages);

        List<ChildMessage> childMessages = (parentMessage.getChildMessages());
        childMessages.add(message);

        parentMessage.setChildMessages(childMessages);

        parentMessageRepo.save(parentMessage);

        return "redirect:/product/" + parentMessage.getProduct().getId();
    }

    @PostMapping("/deleteMessage/parent")
    @PreAuthorize("hasAuthority('USER')")
    public String deleteParentMessage(@RequestParam int id, Authentication authentication) {
        User user = userRepo.findByName(authentication.getName());
        ParentMessage message = parentMessageRepo.findById(id);

        if (user.isAdmin() || user == message.getUser()) {
            int productId = message.getProduct().getId();

            childMessageRepo.deleteAll(childMessageRepo.findAllByParentMessageId(id));
            parentMessageRepo.delete(message);

            return "redirect:/product/" + productId;
        }

        return "redirect:/error";
    }

    @PostMapping("/deleteMessage/child")
    @PreAuthorize("hasAuthority('USER')")
    public String deleteChildMessage(@RequestParam int id, Authentication authentication) {
        User user = userRepo.findByName(authentication.getName());
        ChildMessage message = childMessageRepo.findById(id);

        if (user.isAdmin() || user == message.getUser()) {
            int productId = message.getParentMessage().getProduct().getId();

            List<ChildMessage> messages = message.getParentMessage().getChildMessages();
            messages.remove(message);
            message.getParentMessage().setChildMessages(messages);

            parentMessageRepo.save(message.getParentMessage());

            childMessageRepo.delete(message);

            return "redirect:/product/" + productId;
        }

        return "redirect:/error";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String add(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam int price,
            @RequestParam byte categoryId,
            @RequestParam Set<String> images
    ) {
        List<Product> products = (List<Product>) productRepo.findAll();

        Product product = new Product(name, description, price, categoryRepo.findById(categoryId));

        product.setImages(images);

        products.add(product);

        productRepo.saveAll(products);

        return "redirect:/";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String edit(@RequestParam int id, @RequestParam String name, @RequestParam String description, @RequestParam int price, @RequestParam byte categoryId, @RequestParam(required = false)Set<String> images) {
        if (images == null) {
            return "redirect:/";
        }

        Product product = productRepo.findById(id);

        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setCategory(categoryRepo.findById(categoryId));

        product.setImages(images);

        productRepo.save(product);

        return "redirect:/";
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@RequestParam int id) {
        Product product = productRepo.findById(id);

        for (Object user : sessionRegistry.getAllPrincipals()) {
            ((User)user).getDesired().removeIf(product::equals);
            ((User)user).getCard().removeIf(product::equals);
            userRepo.save((User) user);
        }

        productRepo.delete(product);

        return "redirect:/";
    }
}