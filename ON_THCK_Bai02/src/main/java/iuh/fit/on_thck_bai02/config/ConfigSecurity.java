package iuh.fit.on_thck_bai02.config;

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
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class ConfigSecurity {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            if ("admin".equals(username)) {
                UserDetails admin = User.builder()
                        .username("admin")
                        .password(bCryptPasswordEncoder().encode("123"))
                        .roles("ADMIN")
                        .build();
                return admin;
            } else if ("teacher".equals(username)) {
                UserDetails teacher = User.builder()
                        .username("teacher")
                        .password(bCryptPasswordEncoder().encode("123"))
                        .roles("TEACHER")
                        .build();
                return teacher;
            }
            throw new UsernameNotFoundException("Not found user " + username);
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/home").permitAll()
                        .requestMatchers("/home", "/home/**").permitAll()
                        .requestMatchers("/course/create", "/course/edit/**").hasAnyRole("ADMIN", "TEACHER")
                        .requestMatchers("/course/delete/**", "/admin", "/admin/**").hasRole("ADMIN")
                        .requestMatchers("/course/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/course")
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());
        return http.build();
    }
}
