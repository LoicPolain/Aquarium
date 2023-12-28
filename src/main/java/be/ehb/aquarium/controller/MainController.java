package be.ehb.aquarium.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @GetMapping(value = {"/", "/index"})
    public String getHomepage(){
        return "mainView/Homepage";
    }

    @GetMapping("/login")
    public String login() {
        return "mainView/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
