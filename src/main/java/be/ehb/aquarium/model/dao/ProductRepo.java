package be.ehb.aquarium.model.dao;

import be.ehb.aquarium.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProductRepo extends CrudRepository<Product, UUID> {
}
