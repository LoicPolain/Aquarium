package be.ehb.aquarium.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * This is the configuration file for Spring Security. This file or class defines how Spring Security should handle authentication, authorization, and other security-related aspects in our application.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * @return We are using BCrypt to encode our passwords.
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * This file or class defines how Spring Security should handle authentication, authorization, and other security-related aspects in our application.
     * @param http
     * @return HttpSecurity object
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //Authentication, authorization: Configuration indicating whether a user should access a path, needs to log in, or requires a specific role.
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/register/**").permitAll()
                        .requestMatchers("/productImages/**").permitAll()
                        .requestMatchers("/product/create").hasAuthority("ADMIN")
                        .requestMatchers("/product/cart").authenticated()
                        .requestMatchers("/product/**").authenticated()
                        .requestMatchers("/cart/**").authenticated()
                        .requestMatchers("/").authenticated()
                )
                //Authentication:
                //Configuration pertaining to user authentication.
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                //Configuration associated with user logout.
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll()

                )
                //Configuration associated with user session.
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // A session will always be created if one doesn’t already exist.
                        .invalidSessionUrl("/login") // When there is an invalid session redirect to "/login" => user needs to log back in.
                        .maximumSessions(1) //Maximum 1 session per customer.
                        //.maxSessionsPreventsLogin(true) // Shows an error message to the second attempt instead of forcing the original user to be logged out.
                )
        ;
        return http.build();
    }


    /**
     * Set up global security configurations, such as defining user roles, authentication providers, or other global settings.
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
