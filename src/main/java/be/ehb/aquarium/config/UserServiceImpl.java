package be.ehb.aquarium.config;

import be.ehb.aquarium.model.User;
import be.ehb.aquarium.model.dao.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceImpl implements UserService{
    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void saveUser(User user) {
        userRepo.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepo.findFirstByEmail(email);
    }

    @Override
    public Iterable<User> findAllUsers() {
        return userRepo.findAll();
    }
}
