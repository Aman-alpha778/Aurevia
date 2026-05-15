package com.aurevia.cityexplorer.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class HotelBookingForm {

    @NotBlank
    private String hotelName;

    @NotBlank
    private String guestName;

    @Email
    @NotBlank
    private String email;

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
