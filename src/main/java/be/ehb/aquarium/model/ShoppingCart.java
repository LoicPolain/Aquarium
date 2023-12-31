package be.ehb.aquarium.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToOne
    private User user;
}
