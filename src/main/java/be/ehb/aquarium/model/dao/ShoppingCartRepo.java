package be.ehb.aquarium.model.dao;

import be.ehb.aquarium.model.ShoppingCart;
import be.ehb.aquarium.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ShoppingCartRepo extends CrudRepository<ShoppingCart, UUID> {
    /**
     * Search an user's shopping cart in the DB.
     * @param user
     * @return
     */
    ShoppingCart findFirstByUser(User user);

    /**
     * Search an shopping cart in the DB using its UUID.
     * @param id
     * @return
     */
    ShoppingCart findFirstById(UUID id);
}
