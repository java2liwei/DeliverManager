package com.kural.network.download.constant;

/**
 * 下载信息常量
 */
public class DownloadConstant {

    /*****************************下载状态**************************************/

    public static final int STATE_NOT = 0;

    public static final int STATE_START = 1;

    public static final int STATE_DOWNLOADING = 2;

    public static final int STATE_PASUE = 3;

    public static final int STATE_FINISH = 4;

    public static final int STATE_SUCCESS = 5;

    public static final int STATE_FAIL = 6;



    /*****************************开始下载网络状态**************************************/

    public static final int NETWORK_STATE_NO_WIFI = -1;

    public static final int NetWORK_STATE_MOBILE = 1;

    public static final int NETWORK_STATE_WIFI = 2;

}
