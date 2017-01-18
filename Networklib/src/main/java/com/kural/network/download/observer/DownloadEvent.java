package com.kural.network.download.observer;

/**
 * download event
 */
public class DownloadEvent {


    private int mDownloadState;

    private long mTotalLength;

    private long mCurrentLength;

    private String mUrl;


    public int getDownloadState() {
        return mDownloadState;
    }

    public void setDownloadState(int mDownloadState) {
        this.mDownloadState = mDownloadState;
    }

    public long getTotalLength() {
        return mTotalLength;
    }

    public void setTotalLength(long mTotalLength) {
        this.mTotalLength = mTotalLength;
    }

    public long getCurrentLength() {
        return mCurrentLength;
    }

    public void setCurrentLength(long mCurrentLength) {
        this.mCurrentLength = mCurrentLength;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
