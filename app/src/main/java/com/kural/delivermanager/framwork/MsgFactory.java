package com.kural.delivermanager.framwork;

import com.kural.delivermanager.framwork.msg.BaseMsg;
import com.kural.delivermanager.framwork.msg.DeliverQueryMsg;

import java.util.HashMap;

public class MsgFactory {


    public static final int MSG_ID_DELIVERQUERY = 1;


    public static BaseMsg createMsg (int viewId, int msgId, HashMap<String, String> params) {
        switch (msgId) {
            case MSG_ID_DELIVERQUERY:
                    return new DeliverQueryMsg(viewId, msgId, params);
        }
        return null;
    }


}
