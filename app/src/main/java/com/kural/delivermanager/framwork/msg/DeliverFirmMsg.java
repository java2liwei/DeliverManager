package com.kural.delivermanager.framwork.msg;

import android.text.TextUtils;

import com.kural.delivermanager.base.DeliverOrder;
import com.kural.delivermanager.framwork.constant.MsgConstant;
import com.kural.delivermanager.utils.Base64Util;
import com.kural.delivermanager.utils.Md5Util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * query firm by logistic numb
 */
public class DeliverFirmMsg extends BaseMsg {

    public DeliverFirmMsg (int viewId, int msgId, HashMap<String, String> params) {
        mViewId = viewId;
        mMsgId = msgId;
        mParams = params;
    }


    @Override
    public String getUrl() {
        return "http://api.kdniao.cc/Ebusiness/EbusinessOrderHandle.aspx";
    }


    @Override
    public HashMap<String, String> getParams() {

        if (mParams == null) {
            return null;
        }

        String logisticCode = mParams.get("LogisticCode");
        String requestData= "{'LogisticCode':'" + logisticCode + "'}";
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
            return Base64Util.encode(md5Str.getBytes(charset));
        }

        String md5Str = Md5Util.getStringMd5(content);
        if (TextUtils.isEmpty(md5Str)) {
            return "";
        }
        return Base64Util.encode(md5Str.getBytes(charset));
    }

    @Override
    public Object handleResponse(String string) {
        return DeliverOrder.createFromJson(string);
    }


}
