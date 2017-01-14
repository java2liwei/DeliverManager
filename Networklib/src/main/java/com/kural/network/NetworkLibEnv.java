package com.kural.network;

import android.content.Context;

/**
 * network init
 */
public class NetworkLibEnv {

    public static volatile NetworkLibEnv mInstance;
    private Context mContext;

    public static NetworkLibEnv getInstance() {
        if (mInstance == null) {
            synchronized (NetworkLibEnv.class){
                if (mInstance == null) {
                    mInstance = new NetworkLibEnv();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        if (context == null) {
            throw new RuntimeException("netwoek lib init context is null");
        }
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }


}
