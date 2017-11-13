package com.bewant2be.doit.jcentertest.retrofit;

/**
 * Created by user on 11/11/17.
 */

public class FaceResponse {
    private int errcode;
    private String key;
    private String qrurl;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getQrurl() {
        return qrurl;
    }

    public void setQrurl(String qrurl) {
        this.qrurl = qrurl;
    }
}
