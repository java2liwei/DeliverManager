package com.kural.network.download.utils;

import android.os.Environment;

/**
 * file util
 */
public class FileUtils {


    public static boolean isSDCardExits() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }


}
