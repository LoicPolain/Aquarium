package be.ehb.aquarium.model.dao;

import be.ehb.aquarium.model.ShoppingCart;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ShoppingCartRepo extends CrudRepository<ShoppingCart, UUID> {
}
