package com.kural.network.download.observer;

import android.net.Uri;

/**
 * download event
 */
public class DownloadEvent {

    private Uri mUri;

    private int mDownloadState;

    private long mTotalLength;

    private long mCurrentLength;

    public Uri getUri() {
        return mUri;
    }

    public void setUri(Uri mUri) {
        this.mUri = mUri;
    }

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
}
