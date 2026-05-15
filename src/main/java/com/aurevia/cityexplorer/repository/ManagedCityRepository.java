package com.aurevia.cityexplorer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurevia.cityexplorer.model.ManagedCity;

public interface ManagedCityRepository extends JpaRepository<ManagedCity, Long> {

    Optional<ManagedCity> findBySlug(String slug);

    boolean existsBySlug(String slug);
}
