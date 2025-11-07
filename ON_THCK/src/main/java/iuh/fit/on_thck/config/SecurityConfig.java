package iuh.fit.on_thck.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            if("admin".equals(username)){
                UserDetails admin = User.builder()
                        .username("admin")
                        .password(bCryptPasswordEncoder().encode("123"))
                        .roles("ADMIN")
                        .build();
                return admin;
            }
            if("customer".equals(username)){
                UserDetails customer = User.builder()
                        .username("customer")
                        .password(bCryptPasswordEncoder().encode("123"))
                        .roles("CUSTOMER")
                        .build();
                return customer;
            }
            throw new UsernameNotFoundException("User not found:  "+username);
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/products/create", "/products/edit/**", "/products/delete/**").hasRole("ADMIN")
                        .requestMatchers("/products","/products/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .defaultSuccessUrl("/home")
                                .permitAll())
                .logout(logout -> logout.permitAll());

        return httpSecurity.build();
    }


}
