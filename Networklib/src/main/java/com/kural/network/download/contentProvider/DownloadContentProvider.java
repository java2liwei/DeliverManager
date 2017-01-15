package com.kural.network.download.contentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.kural.network.download.constant.DownloadConstant;
import com.kural.network.download.db.DownloadDbHelper;

/**
 * download content provider
 */
public class DownloadContentProvider extends ContentProvider {

    private DownloadDbHelper mDownloadDBHelper;

    // UriMatcher类用来匹配Uri，使用match()方法匹配路径时返回匹配码
    private static final UriMatcher uriMatcher;

    //操作单个
    private static final int URI_VOLIDE = 1;


    static {
        //初始化uriMatch
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //添加single uri
        uriMatcher.addURI(DownloadConstant.DOWNLOAD_PROVIDER_AUTHORITY, DownloadConstant.DOWNLOAD_TABLE_NAME, URI_VOLIDE);
    }


    @Override
    public boolean onCreate() {
        mDownloadDBHelper = new DownloadDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase sqLiteDatabase = mDownloadDBHelper.getWritableDatabase();
        return sqLiteDatabase.query(DownloadConstant.DOWNLOAD_TABLE_NAME, projection, selection, selectionArgs,
                null, null, sortOrder);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        SQLiteDatabase sqLiteDatabase = mDownloadDBHelper.getWritableDatabase();
        long id = sqLiteDatabase.insert(DownloadConstant.DOWNLOAD_TABLE_NAME, null, contentValues);
        String path = uri.toString();
        return Uri.parse(path.substring(0, path.lastIndexOf("/")) + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = mDownloadDBHelper.getWritableDatabase();
        return sqLiteDatabase.delete(DownloadConstant.DOWNLOAD_TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = mDownloadDBHelper.getWritableDatabase();
        return sqLiteDatabase.update(DownloadConstant.DOWNLOAD_TABLE_NAME, contentValues, selection, selectionArgs);
    }
}
