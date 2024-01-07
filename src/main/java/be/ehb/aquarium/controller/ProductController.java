package be.ehb.aquarium.controller;

import be.ehb.aquarium.model.ShoppingCart;
import be.ehb.aquarium.model.User;
import be.ehb.aquarium.model.dao.ShoppingCartRepo;
import be.ehb.aquarium.model.dao.UserRepo;
import be.ehb.aquarium.model.enums.Category;
import be.ehb.aquarium.model.Product;
import be.ehb.aquarium.model.dao.ProductRepo;
import be.ehb.aquarium.model.enums.Pricefilter;
import be.ehb.aquarium.model.enums.Role;
import jakarta.servlet.http.HttpSession;
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
    private final ProductRepo productRepo;
    private final UserRepo userRepo;
    private final ShoppingCartRepo shoppingCartRepo;

    /**
     * Get the path to the productImages folder
     */
    @Value("${productImage.directory}")
    private final String PRODUCT_IMAGE_FOLDER = System.getProperty("productImage.directory");

    /**
     * @return whether the current user has the role/authority ADMIN
     */
    @ModelAttribute("isCurrentUserAdmin")
    public boolean isAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().contains(Role.ADMIN.toString());
    }

    /**
     * @return a new Product object needed for the form for the creation of a new product.
     */
    @ModelAttribute("productObject")
    public Product getProduct(){
        return new Product();
    }

    /**
     * @return all the products from the DB for the productOverview page.
     */
    @ModelAttribute("products")
    public Iterable<Product> getAllProduct(){
        return productRepo.findAll();
    }

    @Autowired
    public ProductController(ProductRepo productRepo, UserRepo userRepo, ShoppingCartRepo shoppingCartRepo) {
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.shoppingCartRepo = shoppingCartRepo;
    }

    /**
     * GET-method to show the productCreate page.
     * @return the path to the productCreate.html
     */
    @GetMapping(value = "/create")
    public String getProductCreate(){
        return "productView/productCreate";
    }

    /**
     * This POST-method saves the product on the DB and his image on the server if the fields are valid. If the fileds were valid the user is redirected to the productOverview page, else the user stays on the form and gets the validation errors.
     * @param productImage : the product image the user wants to upload
     * @param product : the new product the user created
     * @param bindingResult  : validation results
     * @return returns a view (path to the "create product" form or to the productOverview page) with a model of the created product.
     */
    @PostMapping(value = "/create")
    public ModelAndView postProductAdd(@RequestParam("productImage") MultipartFile productImage, @Valid @ModelAttribute("productObject") Product product, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView("productView/productCreate");
        if (!bindingResult.hasErrors()){
            product = productRepo.save(product);
            modelAndView = new ModelAndView("redirect:/product/overview");

            //This code checks if an image has been uploaded and if so save the image in the corresponding folder and adds its path to the table in the DB.
            if (!productImage.isEmpty()){

                //This code saves the uploaded image on the server in the productImages folder.
                //It creates a new directory with as name the UUID of the product.
                String path = PRODUCT_IMAGE_FOLDER + product.getId();
                Path fileNameAndPath = Paths.get(path, productImage.getOriginalFilename());
                try {
                    Files.createDirectory(Paths.get(path));
                    String imagePath = "../static/productImages/" + product.getId() + "/" + productImage.getOriginalFilename();
                    product.setImagePath(productImage.getOriginalFilename());

                    //We need to save the path of the image in the DB
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

    /**
     * GET-method to show the productLstOverview page.
     * @return the path to the productLstOverview.html
     */
    @GetMapping(value = "/overview")
    public String getProductOverview(){
        return "productView/productLstOverview";
    }

    /**
     * This method returns the products filtered by category.
     * @param category
     * @return modelview to the productOverview page with a model of products (= the products filtered by category) and the model of the selected category.
     */
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

    /**
     * This method returns the products filtered by price.
     * @param priceFilter
     * @return modelview to the productOverview page with a model of products (= the products filtered by price) and the model of the selected priceFilter.
     */
    @PostMapping("/overview/price")
    public ModelAndView postProductOverviewByPrice(@RequestParam("priceFilter")String priceFilter){
        ModelAndView modelAndView = new ModelAndView("productView/productLstOverview");
        if (!priceFilter.isBlank()) {
            if (Pricefilter.valueOf(priceFilter).equals(Pricefilter.ASC)) modelAndView.addObject("products", productRepo.findAllOrderByPriceAscending());
            else modelAndView.addObject("products", productRepo.findAllOrderByPriceDescending());
            modelAndView.addObject("selectedPrice", Pricefilter.valueOf(priceFilter));
        }
        else modelAndView.addObject("products", productRepo.findAll());

        return modelAndView;
    }

    /**
     * @param uuid
     * @return a page with the details of a specific product, it needs the uuid of a product to get it from the DB
     */
    @GetMapping(value = "/details/{id}")
    public ModelAndView getProductDetails(@PathVariable(value = "id")String uuid){
        ModelAndView modelAndView = new ModelAndView("productView/productDetails");
        UUID id = UUID.fromString(uuid);
        modelAndView.addObject("product", productRepo.findFirstById(id));
        return modelAndView;
    }

    /**
     * This POST-method adds a product to the users shopping cart. It links the user-session to the user's shopping cart.
     * @param productId
     * @param httpSession
     * @return redirects to the productOverview page
     */
    @PostMapping("/cart")
    public String postAddProductToCart(@RequestParam("productId") UUID productId, HttpSession httpSession){
        //Get the current logged-in user.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        User tempUser = userRepo.findFirstByEmail(currentUserEmail);

        //Check if user has already a shopping cart in DB, if not create a new ShoppingCart object
        ShoppingCart tempShoppingCart = shoppingCartRepo.findFirstByUser(tempUser);
        if (tempShoppingCart == null) tempShoppingCart = new ShoppingCart();

        //Add the product and the user to the cart and save the cart to the DB
        Product tempProduct = productRepo.findFirstById(productId);
        tempShoppingCart.getProducts().add(tempProduct);
        tempShoppingCart.setUser(tempUser);
        shoppingCartRepo.save(tempShoppingCart);

        //Save cart in userSession
        httpSession.setAttribute("cart", tempShoppingCart);

        //Returns to the productOverview page
        return "redirect:/product/overview";
    }

}
