package com.aurevia.cityexplorer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurevia.cityexplorer.model.ManagedPlace;

public interface ManagedPlaceRepository extends JpaRepository<ManagedPlace, Long> {

    List<ManagedPlace> findAllByOrderByCitySlugAscNameAsc();

    List<ManagedPlace> findByCitySlugOrderByNameAsc(String citySlug);

    void deleteByCitySlug(String citySlug);
}
