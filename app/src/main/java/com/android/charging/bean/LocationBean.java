package com.android.charging.bean;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\21 0021 15:04
 * @class describe
 */
public class LocationBean {
    public String title;
    public double longitude;
    public double latitude;

    public LocationBean(String title,double longitude, double latitude) {
        this.title = title;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "LocationBean{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
