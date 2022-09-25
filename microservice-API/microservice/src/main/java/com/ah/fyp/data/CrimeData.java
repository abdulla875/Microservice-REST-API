package com.ah.fyp.data;

public class CrimeData {
    public String latitude;
    public  String longitude;
    public String category;


    public CrimeData(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getCategory() {
        return category;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
