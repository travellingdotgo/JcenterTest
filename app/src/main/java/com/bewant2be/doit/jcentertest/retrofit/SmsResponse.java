package com.bewant2be.doit.jcentertest.retrofit;

/**
 * Created by user on 11/11/17.
 */

import lombok.Data;

@Data
public class SmsResponse {
    private int errcode;
    private String errmsg;


    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
