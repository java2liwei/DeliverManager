package com.kural.delivermanager.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.kural.delivermanager.R;
import com.kural.delivermanager.base.DeliverBean;
import com.kural.delivermanager.framwork.HttpMsgHandler;
import com.kural.delivermanager.framwork.MsgFactory;
import com.kural.delivermanager.framwork.observer.IMsgObserver;
import com.kural.delivermanager.framwork.observer.ResponseEvent;
import com.kural.delivermanager.framwork.observer.ResponseEventManager;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IMsgObserver {

    private final int VIEW_ID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_download).setOnClickListener(this);

        ResponseEventManager.getInstance().registerMsgObserver(VIEW_ID, this);
    }


    @Override
    public void onClick(View view) {

        HashMap<String, String> params = new HashMap<>();
        params.put("shipperCode", "ZTO");
        params.put("LogisticCode", "424456910166");
        HttpMsgHandler.getInstance().enquene(MsgFactory.createMsg(VIEW_ID, MsgFactory.MSG_ID_DELIVERQUERY, params));
    }

    @Override
    public void onMsgHandle(ResponseEvent event) {

        if (event == null) {
            return;
        }

        DeliverBean deliverBean = (DeliverBean) event.getHandleObj();

    }
}
