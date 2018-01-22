package com.bewant2be.doit.utilslib;

public class StringUtil {

    // get
    public static String clipImageUrl(String url, String add) {
        String temp = null;
        if(url != null) {
            if(add != null) {
                if(url.endsWith(".jpg") || url.endsWith(".png") || url.endsWith(".gif") || url.endsWith(".bmp")) {
                    String end = url.substring(url.length() - 4, url.length());
                    int point = url.lastIndexOf(".");
                    int index = url.lastIndexOf("/");
                    if(index != -1) {
                        String sub = url.substring(index + 1, point);
                        if(!sub.endsWith("_m") && !sub.endsWith("_b") && !sub.endsWith("_s")) {
                            temp = url.substring(0, index + 1) + sub + add + end;
                        } else {
                            String clip = sub.substring(0, sub.length() - 2);
                            temp = url.substring(0, index + 1) + clip + add + end;
                        }
                    }
                }
            } else {
                temp = url;
            }
        }

        return temp;
    }
}
