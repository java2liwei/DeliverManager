package com.kural.network.download.constant;

import android.content.Context;
import android.text.TextUtils;

import com.kural.network.download.utils.FileUtils;

/**
 * 下载信息常量
 */
public class DownloadConstant {


    public static final String DOWNLOAD_TABLE_NAME = "okhttp_download";

    public static final String DOWNLOAD_PROVIDER_AUTHORITY = "com.kural.provider.okhttp_download";

    public static final String DOWNLOAD_PROVIDER_URI = "content://" + DOWNLOAD_PROVIDER_AUTHORITY + "/" + DOWNLOAD_TABLE_NAME;

    /*****************************下载状态**************************************/


    public static final int STATE_PENDDING = 1;

    public static final int STATE_DOWNLOADING = 2;

    public static final int STATE_PASUE = 3;

    public static final int STATE_SUCCESS = 4;

    public static final int STATE_FAIL = 5;



    /*****************************开始下载网络状态**************************************/

    public static final int NetWORK_STATE_MOBILE = 1;

    public static final int NETWORK_STATE_WIFI = 2;


    public static String getDownloadUrl(Context context, String fileName) {

        if (TextUtils.isEmpty(fileName) || context == null) {
            return null;
        }

        String downloadDirPath = null;
        if (FileUtils.isSDCardExits()) {
            downloadDirPath = context.getExternalFilesDir("download").getAbsolutePath();
        } else {
            downloadDirPath = context.getFilesDir().getAbsolutePath();
        }
        return downloadDirPath + "/" + fileName;
    }
}
