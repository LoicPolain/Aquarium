package be.ehb.aquarium.controller;

import be.ehb.aquarium.model.User;
import be.ehb.aquarium.model.dao.UserRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    private final UserRepo userRepo;

    @ModelAttribute("registeredUser")
    public User getUser(){
        return new User();
    }

    @Autowired
    public MainController(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
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
    public String register(@Valid @ModelAttribute("registeredUser") User user, BindingResult bindingResult){
        if (!bindingResult.hasErrors()){
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            user.setConfirmPassword(user.getPassword());
            userRepo.save(user);
            return "redirect:/login";
        }
        return "mainView/register";
    }
}
