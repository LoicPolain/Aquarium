package be.ehb.aquarium.model.dao;

import be.ehb.aquarium.model.enums.Category;
import be.ehb.aquarium.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProductRepo extends CrudRepository<Product, UUID> {
    Product findFirstById(UUID uuid);
    Iterable<Product> findAllByCategory(Category category);

    @Query("SELECT p FROM Product p ORDER BY p.price asc")
    Iterable<Product> findAllOrderByPriceAscedant();

    @Query("SELECT p FROM Product p ORDER BY p.price desc")
    Iterable<Product> findAllOrderByPriceDescedant();
}
