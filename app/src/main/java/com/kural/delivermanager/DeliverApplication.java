package com.kural.delivermanager;

import android.app.Application;
import android.content.Context;

import com.kural.network.NetworkLibEnv;

/**
 * application
 */
public class DeliverApplication extends Application{

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        init();
    }

    public static Context getContext() {
        return mContext;
    }


    private void init() {
        //NetWork Lib init
        NetworkLibEnv.getInstance().init(mContext);

    }

}
