package com.aurevia.cityexplorer.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class BookingForm {

    @NotBlank
    private String flightCode;

    @NotBlank
    private String passengerName;

    @Email
    @NotBlank
    private String email;

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
