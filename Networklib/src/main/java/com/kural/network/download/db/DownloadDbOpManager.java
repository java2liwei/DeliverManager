package com.kural.network.download.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.kural.network.download.constant.DownloadConstant;

/**
 * 下载 数据库操作中心
 */
public class DownloadDbOpManager {

    private static volatile DownloadDbOpManager mInstance;
    private DownloadDbHelper mDownloadDbHelper;

    public static DownloadDbOpManager getInstance() {
        if (mInstance == null) {
            synchronized (DownloadDbOpManager.class) {
                if (mInstance == null) {
                    mInstance = new DownloadDbOpManager();
                }
            }
        }

        return mInstance;
    }


    private DownloadDbOpManager() {
        //TODO init context
        Context context = null;
        mDownloadDbHelper = new DownloadDbHelper(context);
    }


    private void insert(ContentValues contentValues) {

        if (mDownloadDbHelper == null) {
            return;
        }

        if (contentValues == null) {
            return;
        }

        SQLiteDatabase sqLiteDatabase = mDownloadDbHelper.getWritableDatabase();
        sqLiteDatabase.insert(DownloadConstant.DOWNLOAD_TABLE_NAME, null, contentValues);
    }

    private void updata(ContentValues contentValues, String whereClause, String[] whereArgs) {

        if (mDownloadDbHelper == null) {
            return;
        }

        if (contentValues == null) {
            return;
        }

        SQLiteDatabase sqLiteDatabase = mDownloadDbHelper.getWritableDatabase();
        sqLiteDatabase.update(DownloadConstant.DOWNLOAD_TABLE_NAME, contentValues, whereClause, whereArgs);

    }


}
