package be.ehb.aquarium.controller;

import be.ehb.aquarium.model.enums.Category;
import be.ehb.aquarium.model.Product;
import be.ehb.aquarium.model.dao.ProductRepo;
import be.ehb.aquarium.model.enums.Pricefilter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/product")
public class ProductController {
    private ProductRepo productRepo;

    @Value("${productImage.directory}")
    private final String PRODUCT_IMAGE_FOLDER = System.getProperty("productImage.directory");

    @ModelAttribute("productObject")
    public Product getProduct(){
        return new Product();
    }
    @ModelAttribute("products")
    public Iterable<Product> getAllProduct(){
        return productRepo.findAll();
    }
    @Autowired
    public ProductController(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping(value = "/create")
    public String getProductCreate(){
        return "productView/productCreate";
    }

    @PostMapping(value = "/create")
    public ModelAndView postProductAdd(@RequestParam("productImage") MultipartFile productImage, @Valid @ModelAttribute("productObject") Product product, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView("productView/productCreate");
        if (!bindingResult.hasErrors()){
            product = productRepo.save(product);
            modelAndView = new ModelAndView("redirect:/product/overview");

            //This code checks if an image has been uploaded and if so save the image in the corresponding folder and adds its path to the table in the DB.
            if (!productImage.isEmpty()){

                String path = PRODUCT_IMAGE_FOLDER + product.getId();
                Path fileNameAndPath = Paths.get(path, productImage.getOriginalFilename());

                try {
                    Files.createDirectory(Paths.get(path));
                    String imagePath = "../static/productImages/" + product.getId() + "/" + productImage.getOriginalFilename();
                    product.setImagePath(productImage.getOriginalFilename());
                    product = productRepo.save(product);

                    Files.write(fileNameAndPath, productImage.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @GetMapping(value = "/overview")
    public String getProductOverview(){
        return "productView/productLstOverview";
    }

    @PostMapping("/overview/category")
    public ModelAndView postProductOverviewByCategory(@RequestParam("category")String category){
        ModelAndView modelAndView = new ModelAndView("productView/productLstOverview");
        if (!category.isBlank()) {
            modelAndView.addObject("products", productRepo.findAllByCategory(Category.valueOf(category)));
            modelAndView.addObject("selectedCat", Category.valueOf(category));
        }
        else modelAndView.addObject("products", productRepo.findAll());


        return modelAndView;
    }

    @PostMapping("/overview/price")
    public ModelAndView postProductOverviewByPrice(@RequestParam("priceFilter")String priceFilter){
        ModelAndView modelAndView = new ModelAndView("productView/productLstOverview");
        if (!priceFilter.isBlank()) {
            if (Pricefilter.valueOf(priceFilter).equals(Pricefilter.ASC)) modelAndView.addObject("products", productRepo.findAllOrderByPriceAscedant());
            else modelAndView.addObject("products", productRepo.findAllOrderByPriceDescedant());
            modelAndView.addObject("selectedPrice", Pricefilter.valueOf(priceFilter));
        }
        else modelAndView.addObject("products", productRepo.findAll());

        return modelAndView;
    }

    @GetMapping(value = "/details/{id}")
    public ModelAndView getProductOverview(@PathVariable(value = "id")String uuid){
        ModelAndView modelAndView = new ModelAndView("productView/productDetails");
        UUID id = UUID.fromString(uuid);
        modelAndView.addObject("product", productRepo.findFirstById(id));
        return modelAndView;
    }

    @PostMapping("/cart")
    public String postAddProductToCart(@RequestParam("cartObject")String cartObject){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        System.out.println(currentUser);
        return "redirect:/product/overview";
    }

}
