package be.ehb.aquarium.controller;

import be.ehb.aquarium.model.Product;
import be.ehb.aquarium.model.dao.ProductRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepo productRepo;
    @Value("${productImage.directory}")
    private final String PRODUCT_IMAGE_FOLDER = System.getProperty("productImage.directory");
    @GetMapping(value = "/create")
    public String getProductCreate(){
        return "productView/productCreate";
    }

    @ModelAttribute("product")
    public Product getProduct(){
        return new Product();
    }

    @PostMapping(value = "/create")
    public String postProductAdd(@RequestParam("productImage") MultipartFile productImage, @Valid Product product, BindingResult bindingResult){
        if (!bindingResult.hasErrors()){
            product = productRepo.save(product);

            //This code checks if an image has been uploaded and if so save the image in the corresponding folder and adds its path to the table in the DB.
            if (!productImage.isEmpty()){

                String path = PRODUCT_IMAGE_FOLDER + product.getId();
                Path fileNameAndPath = Paths.get(path, productImage.getOriginalFilename());

                try {
                    Files.createDirectory(Paths.get(path));
                    product.setImagePath(path);
                    productRepo.save(product);
                    Files.write(fileNameAndPath, productImage.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return "productView/productLstOverview";
        }
        return "productView/productCreate";
    }

    @GetMapping(value = "/overview")
    public String getProductOverview(){
        return "productView/productLstOverview";
    }
}
