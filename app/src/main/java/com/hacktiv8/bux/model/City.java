package com.hacktiv8.bux.model;

import android.os.Parcel;
import android.os.Parcelable;

public class City implements Parcelable {
    private String id, city;

    public City(){

    }

    public City(String id, String city) {
        this.id = id;
        this.city = city;

    }

    protected City(Parcel in) {
        id = in.readString();
        city = in.readString();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(city);
    }
}
