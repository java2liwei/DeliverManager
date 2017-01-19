package com.kural.delivermanager.framwork.observer;

/**
 * http handler resulte
 */
public class ResponseEvent {


    private int mMsgId;

    private Object mHandleObj;

    public ResponseEvent(int msgId, Object obj) {
        mMsgId = msgId;
        mHandleObj = obj;
    }

    public int getMsgId() {
        return mMsgId;
    }

    public void setMsgId(int mMsgId) {
        this.mMsgId = mMsgId;
    }

    public Object getHandleObj() {
        return mHandleObj;
    }

    public void setHandleObj(Object mHandleObj) {
        this.mHandleObj = mHandleObj;
    }
}
