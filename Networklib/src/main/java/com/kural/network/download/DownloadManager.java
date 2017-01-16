package com.kural.network.download;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import com.kural.network.NetworkLibEnv;
import com.kural.network.download.bean.DownloadInfo;
import com.kural.network.download.constant.DownloadConstant;

/**
 * 下载管理类
 */
public class DownloadManager {

    private static volatile DownloadManager mInstance;


    public static DownloadManager getInstance() {
        if (mInstance == null) {
            synchronized (DownloadManager.class) {
                if (mInstance == null) {
                    mInstance = new DownloadManager();
                }
            }
        }
        return mInstance;
    }

    private DownloadManager () {

    }

    public Uri startDownload(DownloadInfo downloadInfo, boolean isWifi) {

        if (downloadInfo == null) {
            return null;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(DownloadInfo.Column_DownloadUrl, downloadInfo.getDownloadUrl());
        contentValues.put(DownloadInfo.Column_TargetUrl, downloadInfo.getTargetUrl());
        contentValues.put(DownloadInfo.Column_DownloadNetState,
                isWifi ? DownloadConstant.NETWORK_STATE_WIFI : DownloadConstant.NetWORK_STATE_MOBILE);
        contentValues.put(DownloadInfo.Column_DownloadState, DownloadConstant.STATE_PENDDING);

        ContentResolver contentResolver = NetworkLibEnv.getInstance().getContext().getContentResolver();
        Uri uri = Uri.parse(DownloadConstant.DOWNLOAD_PROVIDER_URI);
        return contentResolver.insert(uri, contentValues);
    }

    public void pauseDownload(DownloadInfo downloadInfo, Uri uri) {

        if (downloadInfo == null || uri == null) {
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(DownloadInfo.Column_DownloadState, DownloadConstant.STATE_PASUE);

        ContentResolver contentResolver = NetworkLibEnv.getInstance().getContext().getContentResolver();
        contentResolver.update(uri, contentValues, null, null);
    }

    public void resumeDownload(DownloadInfo downloadInfo, Uri uri) {

        if (downloadInfo == null || uri == null) {
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(DownloadInfo.Column_DownloadState, DownloadConstant.STATE_PENDDING);
        ContentResolver contentResolver = NetworkLibEnv.getInstance().getContext().getContentResolver();
        contentResolver.update(uri, contentValues, null, null);
    }

    public void stopDownload(DownloadInfo downloadInfo, Uri uri) {

        if (downloadInfo == null) {
            return;
        }

        ContentResolver contentResolver = NetworkLibEnv.getInstance().getContext().getContentResolver();
        contentResolver.delete(uri, null, null);
    }

}
