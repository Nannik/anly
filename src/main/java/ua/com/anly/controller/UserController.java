package ua.com.anly.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.anly.entity.Role;
import ua.com.anly.entity.User;
import ua.com.anly.repository.CategoryRepo;
import ua.com.anly.repository.ProductRepo;
import ua.com.anly.repository.UserRepo;
import ua.com.anly.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class UserController {
    private final CategoryRepo categoryRepo;
    private final UserRepo userRepo;
    private final UserService userService;
    private final ProductRepo productRepo;
    private final MainController mainController;
    private final SessionRegistry sessionRegistry;

    public UserController(CategoryRepo categoryRepo, UserRepo userRepo, UserService userService, ProductRepo productRepo, MainController mainController, SessionRegistry sessionRegistry) {
        this.categoryRepo = categoryRepo;
        this.userRepo = userRepo;
        this.userService = userService;
        this.productRepo = productRepo;
        this.mainController = mainController;
        this.sessionRegistry = sessionRegistry;
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userList(Model model) {
        model.addAttribute("categories", categoryRepo.findAll());

        Iterable<User> users = userRepo.findAll();

        model.addAttribute("users", users);

        return "users";
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userPage(@PathVariable String userId, Model model) {
        model.addAttribute("categories", categoryRepo.findAll());

        int id = Integer.parseInt(userId);

        User user = userRepo.findById(id);

        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());

        return "admin/editUser";
    }

    @PostMapping("/user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveUser(@RequestParam int id, @RequestParam String name, @RequestParam String email, @RequestParam Map<String, String> form) {
        userService.updateUser(id, name, userRepo.findById(id).getPassword(), email);
        userService.setUserRoles(id, form);

        return "redirect:/user";
    }

    @GetMapping("/user/{id}/activate/{activationCode}")
    public String activateUser(@PathVariable String id, @PathVariable String activationCode, Model model) {
        User user = userRepo.findById(Integer.parseInt(id));
        model.addAttribute("message", userService.activateUser(user, activationCode, sessionRegistry));

        return mainController.main(model);
    }

    @GetMapping("/sendActivationCode")
    @PreAuthorize("isAuthenticated()")
    public String sendActivationCode(@AuthenticationPrincipal User user) {
        userService.createAndSendEmailActivationCode(user);

        return "redirect:/profile";
    }

    @PostMapping("/registration")
    public String addNewUser (@RequestParam String name, @RequestParam String password, @RequestParam String email, Model model) {
        model.addAttribute("message", userService.addUser(name, password, email));
        return "redirect:/";
    }

    @PostMapping("/user/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUser(@RequestParam int id) {
        userService.deleteUser(id);

        return "redirect:/user";
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model) {
        model.addAttribute("categories", categoryRepo.findAll());

        return "profile";
    }

    @PostMapping("/profile/delete")
    @PreAuthorize("isAuthenticated()")
    public String deleteSelf(@AuthenticationPrincipal User user) {
        userService.deleteUser(user.getId());

        return "redirect:/";
    }

    @GetMapping("/changePassword")
    @PreAuthorize("isAuthenticated()")
    public String sendPasswordActivationCode(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("categories", categoryRepo.findAll());

        model.addAttribute("message", userService.sendPasswordActivationCode(user));

        return "changePassword";
    }

    @PostMapping("/changePassword")
    @PreAuthorize("isAuthenticated()")
    public String changePassword(Model model, @AuthenticationPrincipal User user, @RequestParam String lastPassword, @RequestParam String newPassword, @RequestParam String secretCode) {
        String message = userService.changePassword(user, lastPassword, newPassword, secretCode);
        model.addAttribute("message", message);

        if (message.equals("Password successfully changed")) {
            return mainController.main(model);
        } else {
            model.addAttribute("categories", categoryRepo.findAll());

            return "changePassword";
        }
    }

    @GetMapping("/addToDesired")
    @PreAuthorize("isAuthenticated()")
    public String addToDesired(@RequestParam int productId, @AuthenticationPrincipal User user, HttpServletRequest request) {
        userService.addToDesired(user, productRepo.findById(productId));

        String referer = request.getHeader("referer");

        if (referer == null) {
            return "redirect:/";
        }

        return "redirect:/" + formatRedirect(referer);
    }

    @GetMapping("/removeFromDesired")
    @PreAuthorize("isAuthenticated()")
    public String removeFromDesired(@RequestParam int productId, @AuthenticationPrincipal User user, HttpServletRequest request) {
        userService.removeFromDesired(user, productRepo.findById(productId));

        String referer = request.getHeader("referer");

        if (referer == null) {
            return "redirect:/";
        }

        return "redirect:/" + formatRedirect(referer);
    }

    @GetMapping("/addToCard")
    @PreAuthorize("isAuthenticated()")
    public String addToCard(@RequestParam int productId, @AuthenticationPrincipal User user, HttpServletRequest request) {
        userService.addToCard(user, productRepo.findById(productId));

        String referer = request.getHeader("referer");

        if (referer == null) {
            return "redirect:/";
        }

        return "redirect:/" + formatRedirect(referer);
    }

    @GetMapping("/removeFromCard")
    @PreAuthorize("isAuthenticated()")
    public String removeFromCard(@RequestParam int productId, @AuthenticationPrincipal User user, HttpServletRequest request) {
        userService.removeFromCard(user, productRepo.findById(productId));

        String referer = request.getHeader("referer");

        if (referer == null) {
            return "redirect:/";
        }

        return "redirect:/" + formatRedirect(referer);
    }

    @GetMapping("/removeAllFromCard")
    @PreAuthorize("isAuthenticated()")
    public String removeAllFromCard(@RequestParam int productId, @AuthenticationPrincipal User user, HttpServletRequest request) {
        userService.removeAllFromCard(user, productRepo.findById(productId));

        String referer = request.getHeader("referer");

        if (referer == null) {
            return "redirect:/";
        }

        return "redirect:/" + formatRedirect(referer);
    }

    private String formatRedirect(String referer) {
        StringBuilder redirect = new StringBuilder(referer);

        if (redirect.indexOf("https://") != -1) redirect.delete(0, 8);
        else redirect.delete(0, 7);

        redirect.delete(0, redirect.indexOf("/") + 1);

        return redirect.toString();
    }
}
