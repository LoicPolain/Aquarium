package be.ehb.aquarium.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Currency;

import java.math.BigDecimal;
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
    @PositiveOrZero(message = "Price must be positive!")
    @Digits(integer = 4, fraction = 2, message = "Price must be valid")
    @Currency(value = "EUR", message = "Price must be in euro €")
    private BigDecimal price;
    @Enumerated(EnumType.STRING) //Annotation makes sure that DB stores the category as type String, rather than Integer
    private Category category;

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
