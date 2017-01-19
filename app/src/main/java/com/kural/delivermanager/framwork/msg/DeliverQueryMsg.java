package com.kural.delivermanager.framwork.msg;

import android.text.TextUtils;
import android.util.Base64;

import com.kural.delivermanager.base.DeliverBean;
import com.kural.delivermanager.framwork.constant.MsgConstant;
import com.kural.delivermanager.utils.Md5Util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * deliver query msg
 */
public class DeliverQueryMsg extends BaseMsg {

    public DeliverQueryMsg(int viewId, int msgId, HashMap<String, String> params) {
        mViewId = viewId;
        mMsgId = msgId;
        mParams = params;
    }


    @Override
    public String getUrl() {
        return "http://api.kdniao.cc/Ebusiness/EbusinessOrderHandle.aspx";
    }

    @Override
    public Object handleResponse(String responseStr) {

        if (TextUtils.isEmpty(responseStr)) {
            return null;
        }

        return DeliverBean.createFromJson(responseStr);
    }


    @Override
    public HashMap<String, String> getParams() {

        if (mParams == null) {
            return null;
        }

        String shipperCode = mParams.get("shipperCode");
        String logisticCode = mParams.get("LogisticCode");
        String requestData= "{'OrderCode':'','ShipperCode':'" + shipperCode + "','LogisticCode':'" + logisticCode + "'}";
        HashMap<String, String> params = new HashMap<String, String>();
        try {
            params.put("RequestData", URLEncoder.encode(requestData, "UTF-8"));
            params.put("EBusinessID", MsgConstant.AppId);
            params.put("RequestType", "1002");
            String dataSign = encrypt(requestData, MsgConstant.AppKey, "UTF-8");
            params.put("DataSign", URLEncoder.encode(dataSign, "UTF-8"));
            params.put("DataType", "2");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return params;
    }

    private String encrypt (String content, String keyValue, String charset) throws UnsupportedEncodingException {
        if (!TextUtils.isEmpty(keyValue)) {
            String md5Str = Md5Util.getStringMd5(content + keyValue);
            if (TextUtils.isEmpty(md5Str)) {
                return "";
            }
            return Base64.encodeToString(md5Str.getBytes(), Base64.DEFAULT);
        }

        String md5Str = Md5Util.getStringMd5(content);
        if (TextUtils.isEmpty(md5Str)) {
            return "";
        }
        return Base64.encodeToString(md5Str.getBytes(charset), Base64.DEFAULT);
    }

    @Override
    public int requestType() {
        return MsgConstant.REQUEST_TYPE_POST;
    }
}
