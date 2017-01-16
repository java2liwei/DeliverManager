package com.kural.network.download.observer;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

import com.kural.network.NetworkLibEnv;
import com.kural.network.download.bean.DownloadInfo;
import com.kural.network.download.constant.DownloadConstant;
import com.kural.network.download.db.DownloadDbOpManager;

import java.util.HashSet;

/**
 * 下载状态监听
 */
public class DownloadEventManager extends ContentObserver {

    private static volatile DownloadEventManager mInstance;

    private HashSet<IDownloadEventObserver> mDownloadEvents;

    public static DownloadEventManager getInstance() {
        if (mInstance == null) {
            synchronized (DownloadEventManager.class) {
                if (mInstance == null) {
                    mInstance = new DownloadEventManager();
                }
            }
        }
        return mInstance;
    }

    private void registerDownloadProvider() {
        Uri uri = Uri.parse(DownloadConstant.DOWNLOAD_PROVIDER_URI);
        NetworkLibEnv.getInstance().getContext().getContentResolver().registerContentObserver(uri, false, this);
    }

    private void unregisterDownloadProvider() {
        NetworkLibEnv.getInstance().getContext().getContentResolver().unregisterContentObserver(this);
    }

    private DownloadEventManager() {
        super(new Handler());
        mDownloadEvents = new HashSet<IDownloadEventObserver>();
        registerDownloadProvider();
    }

    public synchronized void registerDownloadEventObserver(IDownloadEventObserver observer) {
        mDownloadEvents.add(observer);
    }

    public synchronized void unregisterDownloadEventObserver(IDownloadEventObserver observer) {
        mDownloadEvents.remove(observer);
    }

    public synchronized void unregisterAllDownloadEventObserver() {
        mDownloadEvents.clear();
    }

    public synchronized void notifyAllDwonloadEvent(DownloadEvent downloadEvent) {
        for (IDownloadEventObserver observer : mDownloadEvents) {
            observer.onDownloadCallBack(downloadEvent);
        }
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {

        DownloadInfo downloadInfo = DownloadDbOpManager.queryDownloadInfo(uri);

        if (downloadInfo != null) {
            DownloadEvent event = new DownloadEvent();
            event.setTotalLength(downloadInfo.getTotalLength());
            event.setCurrentLength(downloadInfo.getCurrentLength());
            event.setDownloadState(downloadInfo.getDownloadState());
            event.setUri(uri);
            notifyAllDwonloadEvent(event);
        }
    }

    public void destroy(){

        unregisterAllDownloadEventObserver();

        unregisterDownloadProvider();

    }
}
