package be.ehb.aquarium.controller;

import be.ehb.aquarium.model.Product;
import be.ehb.aquarium.model.dao.ProductRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepo productRepo;
    @GetMapping(value = "/create")
    public String getProductCreate(){
        return "productView/productCreate";
    }

    @ModelAttribute("product")
    public Product getProduct(){
        return new Product();
    }

    @PostMapping(value = "/create")
    public String postProductAdd(@Valid Product product, BindingResult bindingResult){
        if (!bindingResult.hasErrors()){
            productRepo.save(product);
            return "productView/productLstOverview";
        }
        return "productView/productCreate";
    }

    @GetMapping(value = "/overview")
    public String getProductOverview(){
        return "productView/productLstOverview";
    }
}
