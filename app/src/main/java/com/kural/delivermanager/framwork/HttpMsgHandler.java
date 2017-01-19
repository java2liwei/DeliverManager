package com.kural.delivermanager.framwork;

import android.text.TextUtils;

import com.kural.delivermanager.framwork.constant.MsgConstant;
import com.kural.delivermanager.framwork.msg.BaseMsg;
import com.kural.delivermanager.framwork.observer.ResponseEvent;
import com.kural.delivermanager.framwork.observer.ResponseEventManager;
import com.kural.network.HttpManager;

import java.util.HashMap;
import java.util.concurrent.PriorityBlockingQueue;

import okhttp3.Headers;

/**
 * msg manager conter
 */
public class HttpMsgHandler {

    private volatile static HttpMsgHandler mInstance;

    private PriorityBlockingQueue<BaseMsg> mMsgQuene;

    private MsgConsumThread mMsgConsumThread;

    private boolean mIsQuite = false;

    public HttpMsgHandler getInstance() {
        if (mInstance == null) {
            synchronized (HttpMsgHandler.class) {
                if (mInstance == null) {
                    mInstance = new HttpMsgHandler();
                }
            }
        }
        return mInstance;
    }


    private HttpMsgHandler() {
        mMsgQuene = new PriorityBlockingQueue<>();
        startConsumeThread();
    }


    private void startConsumeThread() {
        mMsgConsumThread = new MsgConsumThread();
        mMsgConsumThread.start();
    }


    public void enquene(BaseMsg baseMsg) {

        if (baseMsg == null) {
            return;
        }

        synchronized (this) {
            mMsgQuene.put(baseMsg);
        }
    }

    public void destroy() {

        setQuiteValue(true);

        if (mMsgConsumThread != null && mMsgConsumThread.isAlive()) {
            mMsgConsumThread.interrupt();
        }
    }


    public synchronized boolean isQuite() {
        return mIsQuite;
    }

    public synchronized void setQuiteValue(boolean isQuite) {
        mIsQuite = isQuite;
    }


    class MsgConsumThread extends Thread {

        @Override
        public void run() {
            BaseMsg baseMsg;
            while (true) {
                try {
                    synchronized (HttpMsgHandler.this) {
                        baseMsg = mMsgQuene.take();
                    }
                    consumeHttpMsg(baseMsg);
                } catch (InterruptedException e) {
                    if (isQuite()) {
                        return;
                    }
                }
            }
        }


        private void consumeHttpMsg(BaseMsg baseMsg) {

            if (baseMsg == null) {
                return;
            }

            String url = baseMsg.getUrl();
            if (TextUtils.isEmpty(url)) {
                return;
            }

            Headers headers = baseMsg.mHeaders;
            HashMap<String, String> params = baseMsg.getParams();

            String responseBodyStr = null;
            int requestType = baseMsg.requestType();
            if (requestType == MsgConstant.REQUEST_TYPE_GET) {
                responseBodyStr = HttpManager.getInstance().getHttpRequest(url, headers);
            } else if (requestType == MsgConstant.REQUEST_TYPE_POST) {
                responseBodyStr = HttpManager.getInstance().postHttpRequest(url, params, headers);
            }

            Object obj = baseMsg.handleResponse(responseBodyStr);

            ResponseEvent responseEvent = new ResponseEvent(baseMsg.mMsgId, obj);

            ResponseEventManager.getInstance().notifyResponseEvent(baseMsg.mViewId, responseEvent);
        }


    }


}
