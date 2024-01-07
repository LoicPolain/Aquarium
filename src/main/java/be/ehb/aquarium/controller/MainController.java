package be.ehb.aquarium.controller;

import be.ehb.aquarium.model.User;
import be.ehb.aquarium.model.dao.UserRepo;
import be.ehb.aquarium.model.enums.Role;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    private final UserRepo userRepo;

    @Autowired
    public MainController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * @return a new user object to be used for the registration form.
     */
    @ModelAttribute("registeredUser")
    public User getUser(){
        return new User();
    }

    /**
     * @return boolean: whether the current user has the role/authority ADMIN (ADMIN = true). This method checks if a current authenticated user is admin. (See usage: navbar link to go to the create product page.)
     */
    @ModelAttribute("isCurrentUserAdmin")
    public boolean isAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(Role.ADMIN.toString()));
        return authentication.getAuthorities().contains(authorities.getFirst());
    }

    /**
     * GET-method to show the homepage.
     * @return the path to the Homepage.html
     */
    @GetMapping(value = {"/", "/index"})
    public String getHomepage(){
        return "mainView/Homepage";
    }

    /**
     * GET-method to show the loginpage.
     * @return the path to the login.html
     */
    @GetMapping("/login")
    public String login() {
        return "mainView/login";
    }

    /**
     * GET-method that redirects to the loginpage.
     * @return redirect to the loginpage
     */
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

    /**
     *  GET-method to show the registerpage.
     * @return the path to the register.html
     */
    @GetMapping("/register")
    public String register() {
        return "mainView/register";
    }

    /**
     * Once the user has sent his registration form, the created user needs to be saved on the DB. First, the User object is checked for errors. If no errors are found the user object is saved on the DB.
     * @param user : The user object that needs to be saved on the DB
     * @param bindingResult : results of validation
     * @return redirect:/login if user fields are valid, else returns register-page in order to show validation errors.
     */
    @PostMapping(value = "/register")
    public String register(@Valid @ModelAttribute("registeredUser") User user, BindingResult bindingResult){
        if (!bindingResult.hasErrors()){
            //Password encoded using BCrypt
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            user.setConfirmPassword(user.getPassword());
            userRepo.save(user);
            return "redirect:/login";
        }
        return "mainView/register";
    }
}
