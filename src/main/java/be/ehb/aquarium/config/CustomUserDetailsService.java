package be.ehb.aquarium.config;

import be.ehb.aquarium.model.User;
import be.ehb.aquarium.model.dao.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * This class is a bridge between the DB and Spring Security. It uses the UserRepo interface to access the DB.
 *
 * This code has been copied from the internet: https://www.javaguides.net/2018/10/user-registration-module-using-springboot-springmvc-springsecurity-hibernate5-thymeleaf-mysql.html
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepo userRepo;

    @Autowired
    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * This method gets the user from the DB. The User model from the DB is than converted to a Spring Security User.
     * @param email Needs the email of the user, in order to search the user in the DB. (String)
     * @return Spring Security-readable User Object.
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findFirstByEmail(email);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
