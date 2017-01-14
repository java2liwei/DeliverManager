package com.kural.network.download.db;

import com.kural.network.download.bean.DownloadInfo;
import com.kural.network.download.constant.DownloadConstant;

/**
 *
 */
public class DbConstant {


    public static final String DB_NAMWE = "download";

    public static final int DB_VERSION = 1;


    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    public static final String CREATE_DOWNLOAD_TABLE =
            "CREATE TABLE IF NOT EXISTS " + DownloadConstant.DOWNLOAD_TABLE_NAME + " (" +
                    DownloadInfo.Column_Id + " INTEGER PRIMARY KEY," +
                    DownloadInfo.Column_DownloadUrl + TEXT_TYPE + COMMA_SEP +
                    DownloadInfo.Column_TotalLength + INT_TYPE + COMMA_SEP +
                    DownloadInfo.Column_CurrentLength + INT_TYPE + COMMA_SEP +
                    DownloadInfo.Column_DownloadNetState + INT_TYPE + COMMA_SEP +
                    DownloadInfo.Column_DownloadState + INT_TYPE + COMMA_SEP +
                    DownloadInfo.Column_TargetUrl + TEXT_TYPE + " )";
}
