package com.kural.network.download.db;

import android.content.ContentValues;
import android.net.Uri;

import com.kural.network.download.bean.DownloadInfo;
import com.kural.network.download.constant.DownloadConstant;

import java.util.ArrayList;

public class DownloadDbOpManager {


    public static int queryDownloadStateById (int id) {
        return DownloadDbBaseOp.getInstance().queryDownloadState("id = ?", new String [] { String.valueOf(id)});
    }

    public static ArrayList<DownloadInfo> queryAllPenddingDownloadInfo () {
        return DownloadDbBaseOp.getInstance().queryDownloadInfos("downloadState = ?", new String [] {String.valueOf(DownloadConstant.STATE_PENDDING)});
    }

    public static void updataDownloadInfoState(int id, int downloadState) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DownloadInfo.Column_DownloadState, downloadState);
        DownloadDbBaseOp.getInstance().updata(contentValues, "id = ?", new String [] {String.valueOf(id)});
    }

    public static void updateDownloadProgress(int id, long totalLength, long currentLength){

        ContentValues contentValues = new ContentValues();
        contentValues.put(DownloadInfo.Column_CurrentLength, currentLength);
        contentValues.put(DownloadInfo.Column_TotalLength, totalLength);

        DownloadDbBaseOp.getInstance().updata(contentValues, "id = ?", new String [] {String.valueOf(id)});
    }

    public static DownloadInfo queryDownloadInfo(Uri uri) {
        return DownloadDbBaseOp.getInstance().query(uri, null, null);
    }


}
