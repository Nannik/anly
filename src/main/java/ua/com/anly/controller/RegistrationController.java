package ua.com.anly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.com.anly.repository.CategoryRepo;

@Controller
public class RegistrationController {
    private final CategoryRepo categoryRepo;

    public RegistrationController(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("categories", categoryRepo.findAll());

        return "registration";
    }
}
