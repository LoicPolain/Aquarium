package be.ehb.aquarium.model.dao;

import be.ehb.aquarium.model.enums.Category;
import be.ehb.aquarium.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProductRepo extends CrudRepository<Product, UUID> {
    /**
     * This method searches products using their UUID.
     * @param uuid
     * @return
     */
    Product findFirstById(UUID uuid);

    /**
     * This is a custom query for the productOverview page, to sort products by category.
     * @param category : the category to order the products
     * @return
     */
    Iterable<Product> findAllByCategory(Category category);


    /**
     * This is a custom query for the productOverview page, to sort products by price ascending.
     * @return product Iterable ordered by price ascending.
     */
    @Query("SELECT p FROM Product p ORDER BY p.price asc")
    Iterable<Product> findAllOrderByPriceAscending();

    /**
     * This is a custom query for the productOverview page, to sort products by price descending.
     * @return product Iterable ordered by price descending.
     */
    @Query("SELECT p FROM Product p ORDER BY p.price desc")
    Iterable<Product> findAllOrderByPriceDescending();
}
