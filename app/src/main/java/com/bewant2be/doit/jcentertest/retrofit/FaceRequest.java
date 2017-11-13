package com.bewant2be.doit.jcentertest.retrofit;

/**
 * Created by user on 11/11/17.
 */

public class FaceRequest {
    private String name;
    private String sex;
    private String mobile;
    private String idno;
    private String address;
    private String file;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}


/*
        <tr><td>姓名</td><td><input type="text" value="hello" name="name"></td></tr>
        <tr><td>性别</td><td><input type="text" value="F" name="sex"></td></tr>
        <tr><td>手机号</td><td><input type="text" value="139" name="mobile"></td></tr>
        <tr><td>身份证号</td><td><input type="text" value="330" name="idno"></td></tr>
        <tr><td>地址</td><td><input type="text" value="浙江省" name="address"></td></tr>
        <tr><td>头像</td><td><input name="photo" type="file"></td></tr>
        <tr><td></td><td><input type="submit" value="submit"></td></tr>
*/