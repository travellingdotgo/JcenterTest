package com.bewant2be.doit.utilslib;

import android.graphics.Bitmap;

/**
 * Created by user on 1/19/18.
 */

public class ImageUtil {


    public static byte[] getGrayscale(Bitmap bitmap) {
        if (bitmap == null)
            return null;

        byte[] ret = new byte[bitmap.getWidth() * bitmap.getHeight()];
        for (int j = 0; j < bitmap.getHeight(); ++j)
            for (int i = 0; i < bitmap.getWidth(); ++i) {
                int pixel = bitmap.getPixel(i, j);
                int red = ((pixel & 0x00FF0000) >> 16);
                int green = ((pixel & 0x0000FF00) >> 8);
                int blue = pixel & 0x000000FF;
                ret[j * bitmap.getWidth() + i] = (byte) ((299 * red + 587
                        * green + 114 * blue) / 1000);
            }
        return ret;
    }


}
