package be.ehb.aquarium.model.dao;

import be.ehb.aquarium.model.ShoppingCart;
import be.ehb.aquarium.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ShoppingCartRepo extends CrudRepository<ShoppingCart, UUID> {
    ShoppingCart findFirstByUser(User user);
}
