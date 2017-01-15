package com.kural.network;

import android.text.TextUtils;

import com.kural.network.download.bean.DownloadInfo;
import com.kural.network.download.constant.DownloadConstant;
import com.kural.network.download.db.DownloadDbOpManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
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

    public String getHttpRequest(String url) {
        return getHttpRequest(url, null);
    }

    public String getHttpRequest(String url, Headers headers) {
        Request request = buildGetRequest(url, headers);
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


    public String postHttpRequest(String url, HashMap<String, String> params) {
        return postHttpRequest(url, params, null);
    }

    public String postHttpRequest(String url, HashMap<String, String> params, Headers headers) {
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


    private void aysnGetHttpRequest(String url, Callback callback) {
        asynGetHttpRequest(url, null, callback);
    }

    private void asynGetHttpRequest(String url, Headers headers, Callback callback) {
        Request request = buildGetRequest(url, headers);
        if (callback == null) {
            throw new RuntimeException("asyn request callback can't be null");
        }
        mHttpClient.newCall(request).enqueue(callback);
    }


    private void aysnPostHttpRequest(String url, HashMap<String, String> params, Callback callback) {
        asynPostHttpRequest(url, params, null, callback);
    }

    private void asynPostHttpRequest(String url, HashMap<String, String> params, Headers headers, Callback callback) {
        Request request = buildPostRequest(url, params, headers);
        if (callback == null) {
            throw new RuntimeException("asyn request callback can't be null");
        }
        mHttpClient.newCall(request).enqueue(callback);
    }


    public void downloadFile(final DownloadInfo downloadInfo) {

        if (downloadInfo == null) {
            return;
        }

        Headers headers = buildDownloadHeaders(downloadInfo);
        Request request = buildGetRequest(downloadInfo.getDownloadUrl(), headers);
        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onDownloadFail(call, e, downloadInfo);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                onDownloadSuccess(call, response, downloadInfo);
            }
        });
    }


    private void onDownloadSuccess(Call call, Response response, DownloadInfo downloadInfo) {

        if (call == null || response == null || downloadInfo == null) {
            return;
        }

        if (downloadInfo.getDownloadState() == DownloadConstant.STATE_PENDDING) {
            DownloadDbOpManager.updataDownloadInfoState(downloadInfo.getId(), DownloadConstant.STATE_DOWNLOADING);
        }

        InputStream ins = response.body().byteStream();
        FileOutputStream ous = null;
        long totalLength = response.body().contentLength();
        long currentLength = downloadInfo.getCurrentLength();
        int downloadState = DownloadConstant.STATE_DOWNLOADING;
        int len = -1;
        byte[] data = new byte[1024 * 4];
        try {

            ous = new FileOutputStream(new File(downloadInfo.getTargetUrl()));

            while ((len = ins.read(data)) != -1 && downloadState == DownloadConstant.STATE_DOWNLOADING) {
                ous.write(data, 0, len);
                currentLength = currentLength + len;
                DownloadDbOpManager.updateDownloadProgress(downloadInfo.getId(), totalLength, currentLength);
                downloadState = DownloadDbOpManager.queryDownloadStateById(downloadInfo.getId());
            }

            DownloadDbOpManager.updataDownloadInfoState(downloadInfo.getId(), DownloadConstant.STATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ins != null) {
                    ins.close();
                }
                if (ous != null) {
                    ous.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void onDownloadFail(Call call, Exception e, DownloadInfo downloadInfo) {

        if (call == null || downloadInfo == null) {
            return;
        }

        DownloadDbOpManager.updataDownloadInfoState(downloadInfo.getId(), DownloadConstant.STATE_FAIL);

    }

    private Request buildGetRequest(String url, Headers headers) {
        if (TextUtils.isEmpty(url)) {
            throw new RuntimeException("url is empty");
        }
        Request.Builder requestBuilder = new Request.Builder();
        if (headers != null) {
            requestBuilder.url(url).headers(headers).build();
        } else {
            requestBuilder.url(url).build();
        }
        return requestBuilder.build();
    }


    private Request buildPostRequest(String url, HashMap<String, String> params, Headers headers) {
        if (TextUtils.isEmpty(url)) {
            throw new RuntimeException("url is empty");
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


    private Headers buildDownloadHeaders(DownloadInfo downloadInfo) {

        if (downloadInfo == null) {
            return null;
        }

        long currentLength = downloadInfo.getCurrentLength();
        Headers.Builder builder = new Headers.Builder();
        if (currentLength > 0) {
            builder.add(com.kural.network.http.Header.RANGE, "bytes=" + currentLength + "-");
        }

        return builder.build();
    }

}
