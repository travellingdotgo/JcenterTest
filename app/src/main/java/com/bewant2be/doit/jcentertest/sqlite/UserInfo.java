package com.bewant2be.doit.jcentertest.sqlite;

/**
 * Created by user on 6/13/17.
 */
public class UserInfo {
    private String user;
    private String time;
    private byte[] feature;
    private byte[] image;

    public UserInfo(String user, String time, byte[] feature, byte[] image) {
        this.user = user;
        this.time = time;
        this.feature = feature;
        this.image = image;
    }

    public String getUser() {
        return user;
    }

    public String getTime() {
        return time;
    }

    public byte[] getFeature() {
        return feature;
    }

    public byte[] getImage() {
        return image;
    }
}
