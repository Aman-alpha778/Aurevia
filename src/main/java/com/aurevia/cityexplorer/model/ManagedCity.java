package com.aurevia.cityexplorer.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class ManagedCity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 500)
    private String tagline;

    @Column(nullable = false)
    private String heroImage;

    @Column(nullable = false)
    private String region;

    @Column(length = 1200)
    private String searchKeywords;

    @Column(length = 200)
    private String bestSeason;

    @Column(length = 200)
    private String idealDuration;

    @Column(length = 2000)
    private String cityHighlights;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ManagedPlace> places = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getHeroImage() {
        return heroImage;
    }

    public void setHeroImage(String heroImage) {
        this.heroImage = heroImage;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<ManagedPlace> getPlaces() {
        return places;
    }

    public String getSearchKeywords() {
        return searchKeywords;
    }

    public void setSearchKeywords(String searchKeywords) {
        this.searchKeywords = searchKeywords;
    }

    public String getBestSeason() {
        return bestSeason;
    }

    public void setBestSeason(String bestSeason) {
        this.bestSeason = bestSeason;
    }

    public String getIdealDuration() {
        return idealDuration;
    }

    public void setIdealDuration(String idealDuration) {
        this.idealDuration = idealDuration;
    }

    public String getCityHighlights() {
        return cityHighlights;
    }

    public void setCityHighlights(String cityHighlights) {
        this.cityHighlights = cityHighlights;
    }
}
