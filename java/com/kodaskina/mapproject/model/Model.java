package com.kodaskina.mapproject.model;

/**
 * Created by EA on 5.7.2017.
 */

public class Model {


    public int userId;
    public double speed;
    public String plaka;
    public String address;
    public String image;
    public double lat;
    public double lnd;


    public Model(){
        //empty constructor...
    }

    public Model(int userId, double speed, String plaka, String address, String image, double lat, double lnd) {
        this.userId = userId;
        this.speed = speed;
        this.plaka = plaka;
        this.address = address;
        this.image = image;
        this.lat = lat;
        this.lnd = lnd;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getPlaka() {
        return plaka;
    }

    public void setPlaka(String plaka) {
        this.plaka = plaka;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLnd() {
        return lnd;
    }

    public void setLnd(double lnd) {
        this.lnd = lnd;
    }

}
