package be.ehb.aquarium.model;

import be.ehb.aquarium.model.customValidation.MatchingPassword;
import be.ehb.aquarium.model.customValidation.UniqueEmail;
import be.ehb.aquarium.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Entity
//This is a custom validation that checks if both passwords fields match (Confirm password)
@MatchingPassword(password = "password", confirmPassword = "confirmPassword", message = "Both Passwords must be the same!")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank(message = "Firstname cannot be empty, blank or whitespace!")
    private String firstname;
    @NotBlank(message = "Lastname cannot be empty, blank or whitespace!")
    private String lastname;
    @NotBlank(message = "Username cannot be empty, blank or whitespace!")
    private String username;
    @Email(message = "E-mail must be valid!") //This is a custom validation that checks if an email does not already exist in the DB. (Unique email)
    @NotBlank(message = "E-mail cannot be empty, blank or whitespace!")
    @UniqueEmail(message = "Email address does already exist!")
    private String email;
    @NotBlank(message = "Password cannot be empty, blank or whitespace!")
    @Size(min = 8, message = "Password must be at least 8 characters long!")
    private String password;

    @Transient //Password only needs to be saved once in the DB, this field is used for validation.
    @NotBlank(message = "Password cannot be empty, blank or whitespace!")
    @Size(min = 8, message = "Password must be at least 8 characters long!")
    private String confirmPassword;
    @Enumerated(EnumType.STRING) //Annotation makes sure that DB stores the category as type String, rather than Integer
    private Role role = Role.USER;
    @OneToOne
    private ShoppingCart shoppingCart;

    public User() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
