package com.aurevia.cityexplorer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurevia.cityexplorer.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
