package com.kural.delivermanager.framwork.observer;

import android.util.SparseArray;

/**
 * response event manager
 */
public class ResponseEventManager {

    private static volatile ResponseEventManager mInstance;

    private SparseArray<IMsgObserver> mMsgObservers;

    public static ResponseEventManager getInstance() {

        if (mInstance == null) {
            synchronized (ResponseEventManager.class) {
                if (mInstance == null) {
                    mInstance = new ResponseEventManager();
                }
            }
        }
        return mInstance;
    }

    public ResponseEventManager () {
        mMsgObservers = new SparseArray<>();
    }


    public synchronized void registerMsgObserver (int viewId, IMsgObserver msgObserver) {
        mMsgObservers.put(viewId, msgObserver);
    }

    public synchronized void unReigsterMsgObserver (int viewId) {
        mMsgObservers.delete(viewId);
    }


    public void notifyResponseEvent(int viewId, ResponseEvent event) {

        IMsgObserver observer = null;
        synchronized (this) {
            observer = mMsgObservers.get(viewId);
        }

        if (observer == null || event == null) {
            return;
        }

        observer.onMsgHandle(event);
    }












}
