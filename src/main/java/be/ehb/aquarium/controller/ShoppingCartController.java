package be.ehb.aquarium.controller;

import be.ehb.aquarium.model.Product;
import be.ehb.aquarium.model.ShoppingCart;
import be.ehb.aquarium.model.User;
import be.ehb.aquarium.model.dao.ProductRepo;
import be.ehb.aquarium.model.dao.ShoppingCartRepo;
import be.ehb.aquarium.model.dao.UserRepo;
import be.ehb.aquarium.model.enums.Role;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {

    private final ShoppingCartRepo shoppingCartRepo;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;

    @Autowired
    public ShoppingCartController(ShoppingCartRepo shoppingCartRepo, UserRepo userRepo, ProductRepo productRepo) {
        this.shoppingCartRepo = shoppingCartRepo;
        this.userRepo = userRepo;
        this.productRepo = productRepo;
    }


    /**
     * @return whether the current user has the role/authority ADMIN
     */
    @ModelAttribute("isCurrentUserAdmin")
    public boolean isAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(Role.ADMIN.toString()));
        return authentication.getAuthorities().contains(authorities.getFirst());
    }

    /**
     * This method looks for the information of the current logged-in user and returns his shopping cart.
     * @return the shopping cart of the logged-in user.
     */
    @ModelAttribute("shoppingCart")
    public ShoppingCart getUsetShoppingCart(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        User currentuser = userRepo.findFirstByEmail(currentUserEmail);
        return shoppingCartRepo.findFirstByUser(currentuser);
    }

    /**
     * @return the total price in BgDecimal of all the products in the shopping cart of the current logged-in user.
     */
    @ModelAttribute("shoppingCartTotalPrice")
    public String getTotalPriceShoppingCart(){
        //Get information current logged-in user.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        User currentuser = userRepo.findFirstByEmail(currentUserEmail);

        //Get the user's shopping cart.
        ShoppingCart tempCart = shoppingCartRepo.findFirstByUser(currentuser);

        //Calculate the total price of the products in the shopping cart.
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        if (tempCart != null){
            for (Product p : tempCart.getProducts()) {
                totalPrice = totalPrice.add(p.getPrice());
            }
        }
        return totalPrice.toString();
    }

    /**
     * GET-method to show the shoppingCart page.
     * @return the path to the shoppingCart.html
     */
    @GetMapping(value = "/overview")
    public String getProductCreate(){
        return "cartView/shoppingCart";
    }

    /**
     * GET-method to show the orderSucces page.
     * @return the path to the orderSucces.html
     */
    @GetMapping(value = "/order")
    public String getOrder(){
        return "cartView/orderSucces";
    }

    /**
     * This POST-method is meant to convert the user's shopping cart to an order.
     * @param shoppingCartId
     * @param httpSession
     * @return the path to the orderSucces.html with a model "succes" (whether the order was successful)
     */
    @PostMapping("/order")
    public ModelAndView postOrder(@RequestParam("cartId") UUID shoppingCartId, HttpSession httpSession){
        //Get the shopping cart from the user-session, if it fails get it from the DB.
        ShoppingCart shoppingCart = (ShoppingCart) httpSession.getAttribute("cart");
        if (shoppingCart == null) shoppingCart = shoppingCartRepo.findFirstById(shoppingCartId);

        //Empty the shopping cart as the products have been ordered.
        if (shoppingCart != null) {
            shoppingCartRepo.delete(shoppingCart);
            httpSession.removeAttribute("cart");
        }

        //return the user to the orderSucces page to tell him the order was successful.
        ModelAndView modelAndView = new ModelAndView("cartView/orderSucces");
        modelAndView.addObject("succes", true);
        return modelAndView;
    }

    /**
     * This POST method removes a product from the user's shopping cart given the product ID.
     * @param productId
     * @param httpSession
     * @return
     */
    @PostMapping("/remove/product")
    public String removeProductFromShoppingCart(@RequestParam("productId") UUID productId, HttpSession httpSession){
        //Get the shopping cart from the user-session, if it fails get it from the DB.
        ShoppingCart shoppingCart = (ShoppingCart) httpSession.getAttribute("cart");
        if (shoppingCart == null) shoppingCart = shoppingCartRepo.findFirstById(getUsetShoppingCart().getId());

        //Remove the product from the shopping cart
        if (shoppingCart != null) {
            Product temp_product = productRepo.findFirstById(productId);
            Set<Product> products = shoppingCart.getProducts();
            products.remove(temp_product);
            shoppingCart.setProducts(products);
            if (shoppingCart.getProducts().isEmpty()) {
                shoppingCartRepo.delete(shoppingCart);
                httpSession.removeAttribute("cart");
            } else {
                //Save cart in DB
                shoppingCartRepo.save(shoppingCart);
                //Save cart in userSession
                httpSession.setAttribute("cart", shoppingCart);
            }
        }
        //return the user to the shopping cart overview.
        return "redirect:/cart/overview";
    }
}
