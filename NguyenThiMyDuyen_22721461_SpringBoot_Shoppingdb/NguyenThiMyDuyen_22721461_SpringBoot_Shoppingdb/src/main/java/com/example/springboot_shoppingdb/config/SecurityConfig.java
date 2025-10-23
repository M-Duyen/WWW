package com.example.springboot_shoppingdb.config;

import com.example.springboot_shoppingdb.entities.Customer;
import com.example.springboot_shoppingdb.repositories.CustomerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(CustomerRepository customerRepository) {
        return username -> {
            if ("admin".equals(username)) {
                UserDetails admin = User.builder()
                        .username("admin")
                        .password(bCryptPasswordEncoder().encode("123"))
                        .roles("ADMIN")
                        .build();
                return admin;
            }
            Optional<Customer> oc = customerRepository.findByName(username);
            if (oc.isPresent()) {
                Customer c = oc.get();
                return User.builder()
                        .username(c.getName())
                        .password(c.getPassword())
                        .roles("CUSTOMER")
                        .build();
            }
            throw new UsernameNotFoundException("User not found: " + username);
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register", "/css/**", "/js/**", "/images/**", "/products", "/products/**").permitAll()
                        .requestMatchers("/orders/**").hasAnyRole("ADMIN", "CUSTOMER")
                        .requestMatchers("/cart/**", "/cart/checkout").hasRole("CUSTOMER")
                        .requestMatchers("/products/create", "/products/edit/**", "/products/delete/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .csrf(Customizer.withDefaults());
        return http.build();
    }


}
