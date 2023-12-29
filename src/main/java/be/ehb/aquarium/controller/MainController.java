package be.ehb.aquarium.controller;

import be.ehb.aquarium.model.User;
import be.ehb.aquarium.model.dao.UserRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;

    @ModelAttribute("registeredUser")
    public User getUser(){
        return new User();
    }

    @Autowired
    public MainController(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }


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

    @GetMapping("/register")
    public String register() {
        return "mainView/register";
    }

    @PostMapping(value = "/register")
    public String register(@Valid User user, BindingResult bindingResult){
        if (!bindingResult.hasErrors()){
            userRepo.save(user);
            return "redirect:/login";
        }
        bindingResult.getAllErrors().forEach(objectError -> System.out.println(objectError.toString()));
        return "redirect:/register";
    }
}
