package com.kural.network.download.bean;

import android.text.TextUtils;

/**
 * Download info
 */
public class DownloadInfo {


    public static final String Column_Id = "id";

    public static final String Column_DownloadUrl = "downloadUrl";

    public static final String Column_TotalLength = "totalLength";

    public static final String Column_DownloadNetState = "downloadNetState";

    public static final String Column_DownloadState = "downloadState";

    public static final String Column_TargetUrl = "tagetUrl";





    private int mId;

    private String mDownloadUrl;

    private long mTotalLength;

    private long mCurrentLength;

    private long mDownloadNetSate;

    private int mDownloadState;

    private String mFileName;

    private String mTargetUrl;

    public DownloadInfo(String downloadUrl) {
        mDownloadUrl = downloadUrl;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
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

    public String getFileName() {

        if (!TextUtils.isEmpty(mFileName)) {
            return mFileName;
        }

        if (TextUtils.isEmpty(mDownloadUrl)) {
            return null;
        }

        int index = mDownloadUrl.lastIndexOf("/");
        if (index == -1) {
            return null;
        }

        mFileName = mDownloadUrl.substring(index);
        return mFileName;
    }

    public void setFileName(String mFileName) {
        this.mFileName = mFileName;
    }

    public String getTargetUrl() {
        return mTargetUrl;
    }

    public void setTargetUrl(String mTargetUrl) {
        this.mTargetUrl = mTargetUrl;
    }
}
