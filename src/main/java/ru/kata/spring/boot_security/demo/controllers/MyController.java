package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/")
public class MyController {

    private final UserService userService;



    public MyController(UserService userService) {
        this.userService = userService;
   ;
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }


    @GetMapping("/user")
    public String getCurrentUserInfo(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", userService.getUserByLogin(user.getLogin()));
        return "show";
    }

    @GetMapping("/admin/users")
    public String getAllUsers(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", userService.getUserByLogin(user.getLogin()));

        return "index";
    }
}
