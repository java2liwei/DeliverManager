package com.kural.network.download;

import com.kural.network.download.bean.DownloadInfo;

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

    public void startDownload(DownloadInfo downloadInfo) {

        if (downloadInfo == null) {
            return;
        }



    }

    public void pauseDownload(DownloadInfo downloadInfo) {

        if (downloadInfo == null) {
            return;
        }

    }

    public void resumeDownload(DownloadInfo downloadInfo) {

        if (downloadInfo == null) {
            return;
        }

    }

    public void stopDownload(DownloadInfo downloadInfo) {

        if (downloadInfo == null) {
            return;
        }

    }

}
