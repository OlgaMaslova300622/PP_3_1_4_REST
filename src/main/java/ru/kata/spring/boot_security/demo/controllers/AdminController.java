package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;


    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }



    @GetMapping("/users")
    public String getAllUsers(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", userService.getUserByLogin(user.getLogin()));
        model.addAttribute("allRoles", roleService.getAllRoles());

        return  "index";
    }


    @GetMapping("/users/{id}")
    public String getUserInfo(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "show";
    }



    @GetMapping("/users/addNewUser")
    public String newUser(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("user", userService.getUserByLogin(user.getLogin()));
        return "new";
    }

    @PostMapping("/users")
    public String createUser(@ModelAttribute("user") @Valid  User user, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleService.getAllRoles());
            return "new";
        }

        userService.saveUser(user);
        return "redirect:/admin/users";
    }



    @PatchMapping("/users/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleService.getAllRoles());
            return "index";
        }

        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);

        return "redirect:/admin/users";

    }

}