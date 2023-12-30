package be.ehb.aquarium.model;

import be.ehb.aquarium.model.customValidation.MatchingPassword;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

@Entity
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
    @Email(message = "E-mail must be valid!")
    @NotBlank(message = "E-mail cannot be empty, blank or whitespace!")
    private String email;
    @NotBlank(message = "Password cannot be empty, blank or whitespace!")
    @Size(min = 8, message = "Password must be at least 8 characters long!")
    private String password;

    @Transient
    @NotBlank(message = "Password cannot be empty, blank or whitespace!")
    @Size(min = 8, message = "Password must be at least 8 characters long!")
    private String confirmPassword;
    @Enumerated(EnumType.STRING) //Annotation makes sure that DB stores the category as type String, rather than Integer
    private Role role = Role.USER;

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
}
