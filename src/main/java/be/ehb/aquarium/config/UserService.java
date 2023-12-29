package be.ehb.aquarium.config;

import be.ehb.aquarium.model.User;

public interface UserService {
    void saveUser(User user);

    User findUserByEmail(String email);

    Iterable<User> findAllUsers();
}
