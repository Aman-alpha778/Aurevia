package com.aurevia.cityexplorer.model;

import jakarta.validation.constraints.NotBlank;

public class AdminCityForm {

    @NotBlank
    private String name;

    @NotBlank
    private String region;

    @NotBlank
    private String tagline;

    @NotBlank
    private String heroImage;

    private String searchKeywords;

    private String bestSeason;

    private String idealDuration;

    private String cityHighlights;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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
