package be.ehb.aquarium.model.dao;

import be.ehb.aquarium.model.User;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface UserRepo extends CrudRepository<User, UUID> {
    public User findFirstByEmail(String email);
}
