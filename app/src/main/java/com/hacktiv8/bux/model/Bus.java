package com.hacktiv8.bux.model;

public class Bus {

    String platno;
    String busName;
    String imgUrl;
    int availableSeats;

    public String getPlatno() {
        return platno;
    }

    public void setPlatno(String platno) {
        this.platno = platno;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}