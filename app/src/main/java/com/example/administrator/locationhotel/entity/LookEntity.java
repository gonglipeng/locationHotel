package com.example.administrator.locationhotel.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/18 0018.
 */
public class LookEntity implements Serializable{
    private int imageView;
    private String km;
    private String lookName;

    public LookEntity(int imageView, String km, String lookName) {
        this.imageView = imageView;
        this.km = km;
        this.lookName = lookName;
    }

    public int getImageView() {
        return imageView;
    }

    public String getKm() {
        return km;
    }

    public String getLookName() {
        return lookName;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public void setLookName(String lookName) {
        this.lookName = lookName;
    }
}
