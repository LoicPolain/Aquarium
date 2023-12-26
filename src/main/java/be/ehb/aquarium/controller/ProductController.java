package be.ehb.aquarium.controller;

import be.ehb.aquarium.model.Product;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {
    @GetMapping(value = "/create")
    public String getProductCreate(){
        return "productView/productCreate";
    }

    @PostMapping(value = "create")
    public String postProductAdd(@Valid Product product, BindingResult bindingResult){
        return "redirect:/overview";
    }

    @GetMapping(value = "/overview")
    public String getProductOverview(){
        return "productLstOverview";
    }
}
