package com.kural.network.download.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.kural.network.HttpManager;
import com.kural.network.download.bean.DownloadInfo;
import com.kural.network.download.constant.DownloadConstant;
import com.kural.network.download.db.DownloadDbOpManager;

import java.util.List;

/**
 * 下载服务， 目的是退出主界面， 下载服务正常运行
 */
public class DownloadService extends Service {


    private DownloadThread mDownloadThread;

    private volatile boolean mIsNeedCheck = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        startDownload();

        return super.onStartCommand(intent, flags, startId);


    }

    private void startDownload() {

        if (isNeedCheck()) {
            return;
        } else {
            changeCheckSatate(true);
        }

        if (mDownloadThread == null || !mDownloadThread.isAlive()) {
            mDownloadThread = new DownloadThread();
            mDownloadThread.start();
        }
    }

    public synchronized boolean isNeedCheck() {
        return mIsNeedCheck;
    }

    public synchronized void changeCheckSatate(boolean mIsNeedCheck) {
        this.mIsNeedCheck = mIsNeedCheck;
    }

    class DownloadThread extends Thread {

        @Override
        public void run() {
            while (isNeedCheck()) {

                changeCheckSatate(false);

                List<DownloadInfo> downloadInfos = DownloadDbOpManager.queryAllPenddingDownloadInfo();
                for (DownloadInfo downloadInfo : downloadInfos) {
                    int state = DownloadDbOpManager.queryDownloadStateById(downloadInfo.getId());
                    if (state == DownloadConstant.STATE_PENDDING) {
                        HttpManager.getInstance().downloadFile(downloadInfo);
                    }
                }
            }
        }
    }


}
