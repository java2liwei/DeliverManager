package com.kural.delivermanager.framwork.msg;

import com.kural.delivermanager.framwork.constant.MsgConstant;

import java.util.HashMap;

import okhttp3.Headers;

/**
 * 基础msg类
 */
public abstract class BaseMsg {

    public int mViewId;

    public int mMsgId;

    protected HashMap<String, String> mParams;

    public Headers mHeaders;

    public String mUrl;

    public abstract  String getUrl();

    public abstract Object handleResponse(String string);

    public boolean isCache() {
        return false;
    }

    public int requestType () {
        return MsgConstant.REQUEST_TYPE_GET;
    }

    public int getPrority() {
        return MsgConstant.PRIORITY_NOMAL;
    }

    public HashMap<String, String> getParams() {
        return mParams;
    }

}
