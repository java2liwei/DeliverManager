package com.kural.network.download.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 下载服务， 目的是退出主界面， 下载服务正常运行
 */
public class DownloadService extends Service{


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
        return super.onStartCommand(intent, flags, startId);
    }




    class DownloadThread extends Thread{

        @Override
        public void run() {
            super.run();


        }
    }


}
