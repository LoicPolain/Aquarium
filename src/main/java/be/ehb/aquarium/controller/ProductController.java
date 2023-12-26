package be.ehb.aquarium.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {
    @GetMapping(value = "/create")
    public String productCreate(){
        return "productView/productCreate";
    }
}
