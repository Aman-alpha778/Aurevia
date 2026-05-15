package com.aurevia.cityexplorer.model;

import jakarta.validation.constraints.NotBlank;

public class AdminPlaceForm {

    @NotBlank
    private String citySlug;

    @NotBlank
    private String categorySlug;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String imageUrl;

    private String galleryImages;

    private String insight;

    private String history;

    private String address;

    private String timings;

    private String priceRange;

    private String detailTitleOne;

    private String detailBodyOne;

    private String detailTitleTwo;

    private String detailBodyTwo;

    private String detailTitleThree;

    private String detailBodyThree;

    private String detailTitleFour;

    private String detailBodyFour;

    private String visitorNotes;

    public String getCitySlug() {
        return citySlug;
    }

    public void setCitySlug(String citySlug) {
        this.citySlug = citySlug;
    }

    public String getCategorySlug() {
        return categorySlug;
    }

    public void setCategorySlug(String categorySlug) {
        this.categorySlug = categorySlug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getGalleryImages() {
        return galleryImages;
    }

    public void setGalleryImages(String galleryImages) {
        this.galleryImages = galleryImages;
    }

    public String getInsight() {
        return insight;
    }

    public void setInsight(String insight) {
        this.insight = insight;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public String getDetailTitleOne() {
        return detailTitleOne;
    }

    public void setDetailTitleOne(String detailTitleOne) {
        this.detailTitleOne = detailTitleOne;
    }

    public String getDetailBodyOne() {
        return detailBodyOne;
    }

    public void setDetailBodyOne(String detailBodyOne) {
        this.detailBodyOne = detailBodyOne;
    }

    public String getDetailTitleTwo() {
        return detailTitleTwo;
    }

    public void setDetailTitleTwo(String detailTitleTwo) {
        this.detailTitleTwo = detailTitleTwo;
    }

    public String getDetailBodyTwo() {
        return detailBodyTwo;
    }

    public void setDetailBodyTwo(String detailBodyTwo) {
        this.detailBodyTwo = detailBodyTwo;
    }

    public String getDetailTitleThree() {
        return detailTitleThree;
    }

    public void setDetailTitleThree(String detailTitleThree) {
        this.detailTitleThree = detailTitleThree;
    }

    public String getDetailBodyThree() {
        return detailBodyThree;
    }

    public void setDetailBodyThree(String detailBodyThree) {
        this.detailBodyThree = detailBodyThree;
    }

    public String getDetailTitleFour() {
        return detailTitleFour;
    }

    public void setDetailTitleFour(String detailTitleFour) {
        this.detailTitleFour = detailTitleFour;
    }

    public String getDetailBodyFour() {
        return detailBodyFour;
    }

    public void setDetailBodyFour(String detailBodyFour) {
        this.detailBodyFour = detailBodyFour;
    }

    public String getVisitorNotes() {
        return visitorNotes;
    }

    public void setVisitorNotes(String visitorNotes) {
        this.visitorNotes = visitorNotes;
    }
}
