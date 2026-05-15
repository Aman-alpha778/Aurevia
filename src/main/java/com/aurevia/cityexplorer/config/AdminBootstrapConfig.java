package com.aurevia.cityexplorer.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.aurevia.cityexplorer.model.User;
import com.aurevia.cityexplorer.service.UserService;

@Configuration
public class AdminBootstrapConfig {

    @Bean
    CommandLineRunner adminBootstrap(UserService userService, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userService.findByEmail("admin@aurevia.com").isEmpty()) {
                User admin = new User();
                admin.setFullName("Aurevia Admin");
                admin.setEmail("admin@aurevia.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("ADMIN");
                userService.save(admin);
            }
        };
    }
}
