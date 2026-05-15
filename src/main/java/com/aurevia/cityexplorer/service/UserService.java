package com.aurevia.cityexplorer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aurevia.cityexplorer.model.AdminUserForm;
import com.aurevia.cityexplorer.model.SignupForm;
import com.aurevia.cityexplorer.model.User;
import com.aurevia.cityexplorer.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email == null ? null : email.toLowerCase().trim());
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email.toLowerCase().trim());
    }

    public User register(SignupForm form) {
        return createUser(form.getFullName(), form.getEmail(), form.getPassword(), "USER");
    }

    public User registerAdmin(AdminUserForm form) {
        return createUser(form.getFullName(), form.getEmail(), form.getPassword(), "ADMIN");
    }

    public List<User> getAdmins() {
        return userRepository.findAll().stream()
                .filter(User::isAdmin)
                .toList();
    }

    public List<User> getUsers() {
        return userRepository.findAll().stream()
                .sorted((left, right) -> {
                    int roleCompare = safeRole(left).compareToIgnoreCase(safeRole(right));
                    if (roleCompare != 0) {
                        return roleCompare;
                    }
                    return left.getFullName().compareToIgnoreCase(right.getFullName());
                })
                .toList();
    }

    private User createUser(String fullName, String email, String password, String role) {
        User user = new User();
        user.setFullName(fullName.trim());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        return userRepository.save(user);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username.toLowerCase().trim())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(safeRole(user))
                .build();
    }

    private String safeRole(User user) {
        String role = user.getRole();
        if (role == null || role.isBlank()) {
            return "USER";
        }
        return role.trim().replaceFirst("(?i)^ROLE_", "").toUpperCase();
    }
}
