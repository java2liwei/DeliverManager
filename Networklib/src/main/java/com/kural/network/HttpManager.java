package com.kural.network;

import android.text.TextUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * network manager
 */
public class HttpManager {


    private static volatile HttpManager mInstance;

    private OkHttpClient mHttpClient;

    public static HttpManager getInstance() {
        if (mInstance == null) {
            synchronized (HttpManager.class) {
                if (mInstance == null) {
                    mInstance = new HttpManager();
                }
            }
        }
        return mInstance;
    }

    private HttpManager() {
        mHttpClient = new OkHttpClient();
    }


    private String aysnGetHttpRequest(String url) {
        return asynGetHttpRequest(url, null);
    }

    private String asynGetHttpRequest(String url, Headers headers) {
        if (TextUtils.isEmpty(url)) {
            throw new RuntimeException("url is empty");
        }

        Request.Builder requestBuilder = new Request.Builder();
        if (headers != null) {
            requestBuilder.url(url).headers(headers).build();
        } else {
            requestBuilder.url(url).build();
        }
        Request request = requestBuilder.build();
        Call call = mHttpClient.newCall(request);
        Response response = null;
        try {
            response = call.execute();
            if (response == null) {
                return null;
            }
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private String aysnPostHttpRequest(String url, HashMap<String, String> params) {
        return asynPostHttpRequest(url, params, null);
    }

    private String asynPostHttpRequest(String url, HashMap<String, String> params, Headers headers) {
        Request request = buildPostRequest(url, params, headers);
        if (request == null) {
            throw new RuntimeException("url is empty");
        }

        Call call = mHttpClient.newCall(request);
        try {
            Response response = call.execute();
            if (response == null) {
                return null;
            }
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Request buildPostRequest(String url, HashMap<String, String> params, Headers headers) {

        if (TextUtils.isEmpty(url)) {
            return null;
        }

        FormBody.Builder builder = null;
        if (params != null && params.size() > 0) {
            builder = new FormBody.Builder();
            Iterator iter = params.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                builder.add((String) entry.getKey(), (String) entry.getValue());
            }
        }

        RequestBody requestBody = null;
        if (builder != null) {
            requestBody = builder.build();
        }

        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url);
        if (requestBody != null) {
            requestBuilder.post(requestBody);
        }

        if (headers != null) {
            requestBuilder.headers(headers);
        }

        return requestBuilder.build();
    }


}
