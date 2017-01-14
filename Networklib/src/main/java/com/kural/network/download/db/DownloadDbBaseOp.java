package com.kural.network.download.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kural.network.NetworkLibEnv;
import com.kural.network.download.bean.DownloadInfo;
import com.kural.network.download.constant.DownloadConstant;
import com.kural.network.download.service.DownloadService;

import java.util.ArrayList;

/**
 * 下载 数据库操作中心
 */
public class DownloadDbBaseOp {

    private static volatile DownloadDbBaseOp mInstance;
    private DownloadDbHelper mDownloadDbHelper;

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


    private DownloadDbBaseOp() {
        mDownloadDbHelper = new DownloadDbHelper(NetworkLibEnv.getInstance().getContext());
    }


    public synchronized void insert(ContentValues contentValues) {

        if (mDownloadDbHelper == null) {
            return;
        }

        if (contentValues == null) {
            return;
        }

        SQLiteDatabase sqLiteDatabase = mDownloadDbHelper.getWritableDatabase();
        sqLiteDatabase.insert(DownloadConstant.DOWNLOAD_TABLE_NAME, null, contentValues);

        updateDownloadThread();
    }

    public synchronized void updata(ContentValues contentValues, String whereClause, String[] whereArgs) {

        if (mDownloadDbHelper == null) {
            return;
        }

        if (contentValues == null) {
            return;
        }

        SQLiteDatabase sqLiteDatabase = mDownloadDbHelper.getWritableDatabase();
        sqLiteDatabase.update(DownloadConstant.DOWNLOAD_TABLE_NAME, contentValues, whereClause, whereArgs);

        updateDownloadThread();

    }

    public synchronized void delet(String whereClause, String[] whereArgs) {

        if (mDownloadDbHelper == null) {
            return;
        }

        SQLiteDatabase sqLiteDatabase = mDownloadDbHelper.getWritableDatabase();
        sqLiteDatabase.delete(DownloadConstant.DOWNLOAD_TABLE_NAME, whereClause, whereArgs);

        updateDownloadThread();
    }


    public DownloadInfo query(String selection, String[] selectionArgs) {

        SQLiteDatabase sqLiteDatabase = mDownloadDbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(DownloadConstant.DOWNLOAD_TABLE_NAME, null, selection,
                selectionArgs, null, null, null);

        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.setId(cursor.getInt(cursor.getColumnIndex(DownloadInfo.Column_Id)));
        downloadInfo.setCurrentLength(cursor.getLong(cursor.getColumnIndex(DownloadInfo.Column_CurrentLength)));
        downloadInfo.setDownloadNetSate(cursor.getInt(cursor.getColumnIndex(DownloadInfo.Column_DownloadNetState)));
        downloadInfo.setDownloadState(cursor.getInt(cursor.getColumnIndex(DownloadInfo.Column_DownloadState)));
        downloadInfo.setDownloadUrl(cursor.getString(cursor.getColumnIndex(DownloadInfo.Column_DownloadUrl)));
        downloadInfo.setTargetUrl(cursor.getString(cursor.getColumnIndex(DownloadInfo.Column_TargetUrl)));
        downloadInfo.setTotalLength(cursor.getLong(cursor.getColumnIndex(DownloadInfo.Column_TotalLength)));

        if (cursor != null) {
            cursor.close();
        }

        return downloadInfo;
    }

    public int queryDownloadState (String selection, String[] selectionArgs) {

        SQLiteDatabase sqLiteDatabase = mDownloadDbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(DownloadConstant.DOWNLOAD_TABLE_NAME,
                new String [] {DownloadInfo.Column_DownloadState}, selection, selectionArgs, null, null, null);
        cursor.moveToFirst();

        int downloadState = cursor.getInt(cursor.getColumnIndex(DownloadInfo.Column_DownloadState));
        if (cursor != null) {
            cursor.close();
        }
        return downloadState;
    }

    public ArrayList<DownloadInfo> queryDownloadInfos (String selection, String[] selectionArgs){

        SQLiteDatabase sqLiteDatabase = mDownloadDbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(DownloadConstant.DOWNLOAD_TABLE_NAME, null, selection,
                selectionArgs, null, null, null);

        ArrayList<DownloadInfo> downloadInfos = new ArrayList<>();

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

        if (cursor != null) {
            cursor.close();
        }

        return downloadInfos;
    }

    public void updateDownloadThread() {
        Context context = NetworkLibEnv.getInstance().getContext();
        Intent intent = new Intent(context, DownloadService.class);
        context.startService(intent);
    }


}
