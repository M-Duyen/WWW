//package com.example.springboot_shoppingdb.security;
//
//import com.example.springboot_shoppingdb.entities.User;
//import com.example.springboot_shoppingdb.repositories.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.*;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Autowired
//    public CustomUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    /**
//     * loadUserByUsername: Spring sẽ truyền giá trị từ form field 'username'.
//     * Trong ứng dụng này 'username' = email, nên ta tìm theo email.
//     * Nếu muốn hỗ trợ cả username/name, bổ sung tìm theo name.
//     */
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // tìm theo email
//        User user = userRepository.findByEmail(username)
//                .orElseGet(() -> userRepository.findByName(username).orElse(null));
//
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found with email/name: " + username);
//        }
//
//        // role trong DB có thể là "admin" hoặc "customer" -> chuyển thành "ROLE_ADMIN"/"ROLE_CUSTOMER"
//        String role = (user.getRole() == null) ? "USER" : user.getRole().toUpperCase();
//        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
//
//        // Use Spring's User implementation (username=email, password must be encoded in DB)
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(user.getEmail() != null ? user.getEmail() : user.getName())
//                .password(user.getPassword() != null ? user.getPassword() : "")
//                .authorities(authorities)
//                .accountExpired(false)
//                .accountLocked(false)
//                .credentialsExpired(false)
//                .disabled(false)
//                .build();
//    }
//}
