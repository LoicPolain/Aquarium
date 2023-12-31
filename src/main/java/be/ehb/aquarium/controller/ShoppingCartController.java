package be.ehb.aquarium.controller;

import be.ehb.aquarium.model.ShoppingCart;
import be.ehb.aquarium.model.User;
import be.ehb.aquarium.model.dao.ShoppingCartRepo;
import be.ehb.aquarium.model.dao.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {

    private final ShoppingCartRepo shoppingCartRepo;
    private final UserRepo userRepo;

    @Autowired
    public ShoppingCartController(ShoppingCartRepo shoppingCartRepo, UserRepo userRepo) {
        this.shoppingCartRepo = shoppingCartRepo;
        this.userRepo = userRepo;
    }

    @ModelAttribute("shoppingCart")
    public ShoppingCart getUsetShoppingCart(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        User currentuser = userRepo.findFirstByEmail(currentUserEmail);
        return shoppingCartRepo.findFirstByUser(currentuser);
    }
    @GetMapping(value = "/overview")
    public String getProductCreate(){
        return "cartView/shoppingCart";
    }
}
