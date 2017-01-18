package com.kural.network.download.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.kural.network.NetworkLibEnv;
import com.kural.network.download.bean.DownloadInfo;
import com.kural.network.download.constant.DownloadConstant;

import java.util.ArrayList;

/**
 * 下载 数据库操作中心
 */
public class DownloadDbBaseOp {

    private static volatile DownloadDbBaseOp mInstance;

    public static DownloadDbBaseOp getInstance() {
        if (mInstance == null) {
            synchronized (DownloadDbBaseOp.class) {
                if (mInstance == null) {
                    mInstance = new DownloadDbBaseOp();
                }
            }
        }
        return mInstance;
    }


    public synchronized void updata(ContentValues contentValues, String whereClause, String[] whereArgs) {

        Context context = NetworkLibEnv.getInstance().getContext();
        if (context == null) {
            return;
        }

        if (contentValues == null) {
            return;
        }

        Uri uri = Uri.parse(DownloadConstant.DOWNLOAD_PROVIDER_URI);
        context.getContentResolver().update(uri, contentValues, whereClause, whereArgs);
    }

    public DownloadInfo query(Uri uri, String selection, String[] selectionArgs) {

        Context context = NetworkLibEnv.getInstance().getContext();
        if (context == null || uri == null) {
            return null;
        }

        Cursor cursor = context.getContentResolver().query(uri, null, selection, selectionArgs, null);
        if (cursor == null) {
            return null;
        }

        cursor.moveToFirst();
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.setId(cursor.getInt(cursor.getColumnIndex(DownloadInfo.Column_Id)));
        downloadInfo.setCurrentLength(cursor.getLong(cursor.getColumnIndex(DownloadInfo.Column_CurrentLength)));
        downloadInfo.setDownloadNetSate(cursor.getInt(cursor.getColumnIndex(DownloadInfo.Column_DownloadNetState)));
        downloadInfo.setDownloadState(cursor.getInt(cursor.getColumnIndex(DownloadInfo.Column_DownloadState)));
        downloadInfo.setDownloadUrl(cursor.getString(cursor.getColumnIndex(DownloadInfo.Column_DownloadUrl)));
        downloadInfo.setTargetUrl(cursor.getString(cursor.getColumnIndex(DownloadInfo.Column_TargetUrl)));
        downloadInfo.setTotalLength(cursor.getLong(cursor.getColumnIndex(DownloadInfo.Column_TotalLength)));
        cursor.close();

        return downloadInfo;
    }

    public DownloadInfo query(String selection, String[] selectionArgs) {

        Context context = NetworkLibEnv.getInstance().getContext();
        if (context == null) {
            return null;
        }

        Uri uri = Uri.parse(DownloadConstant.DOWNLOAD_PROVIDER_URI);
        Cursor cursor = context.getContentResolver().query(uri, null, selection, selectionArgs, null);
        if (cursor == null) {
            return null;
        }

        cursor.moveToFirst();
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.setId(cursor.getInt(cursor.getColumnIndex(DownloadInfo.Column_Id)));
        downloadInfo.setCurrentLength(cursor.getLong(cursor.getColumnIndex(DownloadInfo.Column_CurrentLength)));
        downloadInfo.setDownloadNetSate(cursor.getInt(cursor.getColumnIndex(DownloadInfo.Column_DownloadNetState)));
        downloadInfo.setDownloadState(cursor.getInt(cursor.getColumnIndex(DownloadInfo.Column_DownloadState)));
        downloadInfo.setDownloadUrl(cursor.getString(cursor.getColumnIndex(DownloadInfo.Column_DownloadUrl)));
        downloadInfo.setTargetUrl(cursor.getString(cursor.getColumnIndex(DownloadInfo.Column_TargetUrl)));
        downloadInfo.setTotalLength(cursor.getLong(cursor.getColumnIndex(DownloadInfo.Column_TotalLength)));
        cursor.close();

        return downloadInfo;
    }

    public int queryDownloadState (String selection, String[] selectionArgs) {

        Context context = NetworkLibEnv.getInstance().getContext();
        if (context == null) {
            return -1;
        }

        Uri uri = Uri.parse(DownloadConstant.DOWNLOAD_PROVIDER_URI);
        Cursor cursor = context.getContentResolver().query(uri, new String [] {DownloadInfo.Column_DownloadState}, selection, selectionArgs, null);
        if (cursor == null) {
            return -1;
        }
        cursor.moveToFirst();
        int downloadState = cursor.getInt(cursor.getColumnIndex(DownloadInfo.Column_DownloadState));
        cursor.close();
        return downloadState;
    }

    public ArrayList<DownloadInfo> queryDownloadInfos (String selection, String[] selectionArgs){

        Context context = NetworkLibEnv.getInstance().getContext();
        if (context == null) {
            return null;
        }

        Uri uri = Uri.parse(DownloadConstant.DOWNLOAD_PROVIDER_URI);
        Cursor cursor = context.getContentResolver().query(uri, null, selection, selectionArgs, null);
        ArrayList<DownloadInfo> downloadInfos = new ArrayList<>();

        if (cursor == null) {
            return downloadInfos;
        }

        while (cursor.moveToNext()) {
            DownloadInfo downloadInfo = new DownloadInfo();
            downloadInfo.setId(cursor.getInt(cursor.getColumnIndex(DownloadInfo.Column_Id)));
            downloadInfo.setCurrentLength(cursor.getLong(cursor.getColumnIndex(DownloadInfo.Column_CurrentLength)));
            downloadInfo.setDownloadNetSate(cursor.getInt(cursor.getColumnIndex(DownloadInfo.Column_DownloadNetState)));
            downloadInfo.setDownloadState(cursor.getInt(cursor.getColumnIndex(DownloadInfo.Column_DownloadState)));
            downloadInfo.setDownloadUrl(cursor.getString(cursor.getColumnIndex(DownloadInfo.Column_DownloadUrl)));
            downloadInfo.setTargetUrl(cursor.getString(cursor.getColumnIndex(DownloadInfo.Column_TargetUrl)));
            downloadInfo.setTotalLength(cursor.getLong(cursor.getColumnIndex(DownloadInfo.Column_TotalLength)));
            downloadInfos.add(downloadInfo);
        }

        cursor.close();
        return downloadInfos;
    }
}
