package be.ehb.aquarium.model.dao;

import be.ehb.aquarium.model.User;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface UserRepo extends CrudRepository<User, UUID> {
    /**
     * Searches an user in the DB using its email.
     * @param email
     * @return
     */
    public User findFirstByEmail(String email);
}
