package com.hacktiv8.bux.model;

public class Order {
    String bookNo, departHour, busName, platBus, departTerminal, departCity, status;
    Long date;

    public String getBookNo() {
        return bookNo;
    }

    public void setBookNo(String bookNo) {
        this.bookNo = bookNo;
    }

    public String getDepartHour() {
        return departHour;
    }

    public void setDepartHour(String departHour) {
        this.departHour = departHour;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getPlatBus() {
        return platBus;
    }

    public void setPlatBus(String platBus) {
        this.platBus = platBus;
    }

    public String getDepartTerminal() {
        return departTerminal;
    }

    public void setDepartTerminal(String departTerminal) {
        this.departTerminal = departTerminal;
    }

    public String getDepartCity() {
        return departCity;
    }

    public void setDepartCity(String departCity) {
        this.departCity = departCity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
