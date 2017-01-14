package com.kural.network.download;

import android.content.ContentValues;

import com.kural.network.download.bean.DownloadInfo;
import com.kural.network.download.constant.DownloadConstant;
import com.kural.network.download.db.DownloadDbBaseOp;

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

    public void startDownload(DownloadInfo downloadInfo, boolean isWifi) {

        if (downloadInfo == null) {
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(DownloadInfo.Column_DownloadUrl, downloadInfo.getDownloadUrl());
        contentValues.put(DownloadInfo.Column_TargetUrl, downloadInfo.getTargetUrl());
        contentValues.put(DownloadInfo.Column_DownloadNetState,
                isWifi ? DownloadConstant.NETWORK_STATE_WIFI : DownloadConstant.NetWORK_STATE_MOBILE);
        contentValues.put(DownloadInfo.Column_DownloadState, DownloadConstant.STATE_PENDDING);

        DownloadDbBaseOp.getInstance().insert(contentValues);

    }

    public void pauseDownload(DownloadInfo downloadInfo) {

        if (downloadInfo == null) {
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(DownloadInfo.Column_DownloadState, DownloadConstant.STATE_PASUE);
        DownloadDbBaseOp.getInstance().updata(contentValues, "id = ?", new String[]{String.valueOf(downloadInfo.getId())});

    }

    public void resumeDownload(DownloadInfo downloadInfo) {

        if (downloadInfo == null) {
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(DownloadInfo.Column_DownloadState, DownloadConstant.STATE_PENDDING);
        DownloadDbBaseOp.getInstance().updata(contentValues, "id = ?", new String[]{String.valueOf(downloadInfo.getId())});

    }

    public void stopDownload(DownloadInfo downloadInfo) {

        if (downloadInfo == null) {
            return;
        }
        DownloadDbBaseOp.getInstance().delet("id = ?", new String[]{String.valueOf(downloadInfo.getId())});


    }

}
