package be.ehb.aquarium.controller;

import be.ehb.aquarium.model.Product;
import be.ehb.aquarium.model.ShoppingCart;
import be.ehb.aquarium.model.User;
import be.ehb.aquarium.model.dao.ShoppingCartRepo;
import be.ehb.aquarium.model.dao.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.UUID;

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

    @ModelAttribute("shoppingCartTotalPrice")
    public String getTotalPriceShoppingCart(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        User currentuser = userRepo.findFirstByEmail(currentUserEmail);
        ShoppingCart tempCart = shoppingCartRepo.findFirstByUser(currentuser);
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        if (tempCart != null){
            for (Product p : tempCart.getProducts()) {
                totalPrice = totalPrice.add(p.getPrice());
            }
        }
        return totalPrice.toString();
    }
    @GetMapping(value = "/overview")
    public String getProductCreate(){
        return "cartView/shoppingCart";
    }

    @GetMapping(value = "/order")
    public String getOrder(){
        return "cartView/orderSucces";
    }

    @PostMapping("/order")
    public ModelAndView postOrder(@RequestParam("cartId") UUID shoppingCartId, HttpSession httpSession){
        ShoppingCart shoppingCart = (ShoppingCart) httpSession.getAttribute("cart");
        if (shoppingCart == null) shoppingCart = shoppingCartRepo.findFirstById(shoppingCartId);
        if (shoppingCart != null) {
            shoppingCartRepo.delete(shoppingCart);
            httpSession.removeAttribute("cart");
        }
        ModelAndView modelAndView = new ModelAndView("cartView/orderSucces");
        modelAndView.addObject("succes", true);
        return modelAndView;
    }
}
