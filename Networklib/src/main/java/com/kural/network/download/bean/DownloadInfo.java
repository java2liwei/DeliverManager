package com.kural.network.download.bean;

/**
 * Download info
 */
public class DownloadInfo {

    private String mDownloadUrl;

    private long mTotalLength;

    private long mCurrentLength;

    private long mDownloadNetSate;

    private int mDownloadState;

    public DownloadInfo (String downloadUrl) {
        mDownloadUrl = downloadUrl;
    }


    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    public void setDownloadUrl(String mDownloadUrl) {
        this.mDownloadUrl = mDownloadUrl;
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

    public long getDownloadNetSate() {
        return mDownloadNetSate;
    }

    public void setDownloadNetSate(long mDownloadNetSate) {
        this.mDownloadNetSate = mDownloadNetSate;
    }

    public int getDownloadState() {
        return mDownloadState;
    }

    public void setDownloadState(int mDownloadState) {
        this.mDownloadState = mDownloadState;
    }
}
