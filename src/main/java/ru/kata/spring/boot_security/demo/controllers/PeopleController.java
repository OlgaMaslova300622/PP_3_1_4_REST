package ru.kata.spring.boot_security.demo.controllers;



import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;


@Controller
@RequestMapping("/")
public class PeopleController {

    private final UserService userService;

    private final RoleService roleService;


    public PeopleController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }



    @GetMapping("/admin/users")
    public String getAllUsers(Model model){
        //Получим всех людей из DAO и передадим на отображение в представление

       model.addAttribute("users", userService.getAllUsers());

        return  "index"; //файл index.html
   }

   @GetMapping("/user")
   public String getCurrentUserInfo(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        return "show";
   }

   @GetMapping("/admin/users/{id}")
   public String getUserInfo(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "show"; // файл show.html
   }



    @GetMapping("/admin/users/addNewUser")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("allRoles", roleService.getAllRoles());

        return "new"; // файл new.html
    }

    @PostMapping("/admin/users")
    public String createUser(@ModelAttribute("user") @Valid  User user, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleService.getAllRoles());
            return "new";
        }

        userService.saveUser(user);
        return "redirect:/admin/users";
    }


    @GetMapping("/admin/users/{id}/edit")
    public String editUser(Model model, @PathVariable("id") int id){
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "edit";
    }



    @PatchMapping("/admin/users/{id}/edit")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleService.getAllRoles());
            return "edit";
        }

        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/admin/users/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);

        return "redirect:/admin/users";

    }

}
