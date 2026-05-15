package com.aurevia.cityexplorer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aurevia.cityexplorer.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @EntityGraph(attributePaths = "user")
    List<Review> findTop8ByCitySlugAndCategorySlugIsNullAndPlaceSlugIsNullOrderByCreatedAtDesc(String citySlug);

    @EntityGraph(attributePaths = "user")
    List<Review> findTop8ByCitySlugAndCategorySlugAndPlaceSlugIsNullOrderByCreatedAtDesc(String citySlug, String categorySlug);

    @EntityGraph(attributePaths = "user")
    List<Review> findTop8ByCitySlugAndCategorySlugAndPlaceSlugOrderByCreatedAtDesc(String citySlug, String categorySlug, String placeSlug);
}
