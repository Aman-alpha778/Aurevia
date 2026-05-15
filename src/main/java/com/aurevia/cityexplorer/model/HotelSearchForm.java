package com.aurevia.cityexplorer.model;

public class HotelSearchForm {

    private String city = "Chandigarh";
    private String checkIn = "";
    private int guests = 2;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }
}
