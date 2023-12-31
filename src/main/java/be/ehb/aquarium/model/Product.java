package be.ehb.aquarium.model;

import be.ehb.aquarium.model.enums.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank(message = "Name is mandatory!")
    private String productName;
    private String description;
    private String imagePath;
    @NotNull(message = "You need to enter a price")
    @PositiveOrZero(message = "Price must be positive!")
    @Digits(integer = 4, fraction = 2, message = "Price must be valid")
    private BigDecimal price;
    @Enumerated(EnumType.STRING) //Annotation makes sure that DB stores the category as type String, rather than Integer
    @NotNull(message = "You need to select a category!")
    private Category category;

    @ManyToMany
    @JoinTable(name = "product_shoppingcart", joinColumns = @JoinColumn(name = "product_id", nullable = false, referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "shoppingCarts_id", nullable = false, referencedColumnName = "id"))
    private Set<ShoppingCart> shoppingCarts = new HashSet<>();

    public Product() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
