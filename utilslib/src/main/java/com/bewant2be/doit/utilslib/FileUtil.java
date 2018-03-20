package com.bewant2be.doit.utilslib;

import android.content.Context;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by user on 4/22/17.
 */
public class FileUtil {
    public static byte[] assets2bytes(Context context, String filename) {
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int count = -1;
        try {
            inputStream = context.getAssets().open(filename);
            while ((count = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, count);
            }
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return byteArrayOutputStream.toByteArray();
    }

    public static void touchFolder(String folderpath){
        /*
        final String FRAME_DUMP_FOLDER_PATH = Environment.getExternalStorageDirectory()
                                                + File.separator + "haha";
        */
        File dumpFolder = new File(folderpath);
        if (!dumpFolder.exists()) {
            dumpFolder.mkdirs();
        }
    }


    public static String[] getAssetsPath(Context context,String path) throws IOException {
        String fileNames[] = context.getAssets().list(path);
        return fileNames;
    }
}
