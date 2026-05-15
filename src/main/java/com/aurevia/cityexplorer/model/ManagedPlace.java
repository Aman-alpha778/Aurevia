package com.aurevia.cityexplorer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ManagedPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String citySlug;

    @ManyToOne(optional = true)
    @JoinColumn(name = "city_id")
    private ManagedCity city;

    @Column(nullable = false)
    private String categorySlug;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private String imageUrl;

    @Column(length = 4000)
    private String galleryImages;

    @Column(length = 2500)
    private String insight;

    @Column(length = 2500)
    private String history;

    @Column(length = 500)
    private String address;

    @Column(length = 500)
    private String timings;

    @Column(length = 500)
    private String priceRange;

    @Column(length = 200)
    private String detailTitleOne;

    @Column(length = 1200)
    private String detailBodyOne;

    @Column(length = 200)
    private String detailTitleTwo;

    @Column(length = 1200)
    private String detailBodyTwo;

    @Column(length = 200)
    private String detailTitleThree;

    @Column(length = 1200)
    private String detailBodyThree;

    @Column(length = 200)
    private String detailTitleFour;

    @Column(length = 1200)
    private String detailBodyFour;

    @Column(length = 2500)
    private String visitorNotes;

    public Long getId() {
        return id;
    }

    public ManagedCity getCity() {
        return city;
    }

    public void setCity(ManagedCity city) {
        this.city = city;
        if (city != null) {
            this.citySlug = city.getSlug();
        }
    }

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
