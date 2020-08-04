package ua.com.anly.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.anly.entity.Category;
import ua.com.anly.repository.CategoryRepo;
import ua.com.anly.repository.ProductRepo;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private final CategoryRepo categoryRepo;
    private final ProductRepo productRepo;
    private final ApplicationContext applicationContext;

    public CategoryController(CategoryRepo categoryRepo, ProductRepo productRepo, ApplicationContext applicationContext) {
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
        this.applicationContext = applicationContext;
    }

    @GetMapping
    public String main(Model model) {
        return applicationContext.getBean(MainController.class).main(model);
    }

    @GetMapping("/{id}")
    public String categoryPage(@PathVariable String id, Model model) {
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("categoryId", Integer.parseInt(id));

        model.addAttribute("products", productRepo.findAllByCategoryId(Integer.parseInt(id)));

        return "main";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addCategoryPage(Model model) {
        model.addAttribute("categories", categoryRepo.findAll());

        return "admin/addCategory";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editCategoryPage(@PathVariable String id, Model model) {
        model.addAttribute("categories", categoryRepo.findAll());

        Category category = categoryRepo.findById(Integer.parseInt(id));

        if (category == null){
            model.addAttribute("error", "404 - not found");
            return "error";
        }

        model.addAttribute("category", category);

        return "admin/editCategory";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addCategory(@RequestParam String name) {
        List<Category> categories = (List<Category>) categoryRepo.findAll();

        categories.add(new Category(name));

        categoryRepo.saveAll(categories);

        return "redirect:/";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editCategory(@RequestParam int id, @RequestParam String name) {
        Category category = categoryRepo.findById(id);

        category.setName(name);

        categoryRepo.save(category);

        return "redirect:/category/" + id;
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteCategory(@RequestParam int id) {
        productRepo.deleteAll(productRepo.findAllByCategoryId(id));

        categoryRepo.delete(categoryRepo.findById(id));

        return "redirect:/";
    }
}
