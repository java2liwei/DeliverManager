package com.kural.delivermanager.base;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 EBusinessID	String	用户ID	R
 OrderCode	String	订单编号	O
 ShipperCode	String	快递公司编码	R
 LogisticCode	String	物流运单号	O
 Success	Bool	成功与否	R
 Reason	String	失败原因	O
 State	String	物流状态：2-在途中,3-签收,4-问题件	R
 */

public class DeliverBean {

    private String EBusinessID;

    private String OrderCode;

    private String ShipperCode;

    private String LogisticCode;

    private Boolean Success;

    private String Reason;

    private String State;

    private ArrayList<DeliverTrace> deliverTraces;

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String orderCode) {
        OrderCode = orderCode;
    }

    public String getShipperCode() {
        return ShipperCode;
    }

    public void setShipperCode(String shipperCode) {
        ShipperCode = shipperCode;
    }

    public String getLogisticCode() {
        return LogisticCode;
    }

    public void setLogisticCode(String logisticCode) {
        LogisticCode = logisticCode;
    }

    public Boolean getSuccess() {
        return Success;
    }

    public void setSuccess(Boolean success) {
        Success = success;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public ArrayList<DeliverTrace> getDeliverTraces() {
        return deliverTraces;
    }

    public void setDeliverTraces(ArrayList<DeliverTrace> deliverTraces) {
        this.deliverTraces = deliverTraces;
    }

    public static DeliverBean createFromJson(String responseStr) {

        DeliverBean deliverBean = new DeliverBean();
        if (TextUtils.isEmpty(responseStr)) {
            return deliverBean;
        }

        try {
            JSONObject deliverJson = new JSONObject(responseStr);
            if (!deliverJson.isNull("EBusinessID")) {
                deliverBean.setEBusinessID(deliverJson.optString("EBusinessID"));
            }

            if (!deliverJson.isNull("OrderCode")) {
                deliverBean.setOrderCode(deliverJson.optString("OrderCode"));
            }

            if (!deliverJson.isNull("ShipperCode")) {
                deliverBean.setShipperCode(deliverJson.optString("ShipperCode"));
            }

            if (!deliverJson.isNull("LogisticCode")) {
                deliverBean.setLogisticCode(deliverJson.optString("LogisticCode"));
            }

            if (!deliverJson.isNull("Success")) {
                deliverBean.setSuccess(deliverJson.optBoolean("Success"));
            }

            if (!deliverJson.isNull("Reason")) {
                deliverBean.setReason(deliverJson.optString("Reason"));
            }

            if (!deliverJson.isNull("State")) {
                deliverBean.setState(deliverJson.optString("State"));
            }

            if (!deliverJson.isNull("Traces")) {
                String traces = deliverJson.optString("Traces");
                deliverBean.setDeliverTraces(getDeliverTraces(traces));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return deliverBean;
    }


    public static ArrayList<DeliverTrace> getDeliverTraces(String traces) {

        ArrayList<DeliverTrace> deliverTraces = new ArrayList<>();

        if (TextUtils.isEmpty(traces)) {
            return deliverTraces;
        }

        try {
            JSONArray jsonArray = new JSONArray(traces);
            DeliverTrace deliverTrace;
            for (int i=0; i<jsonArray.length(); i++) {

                JSONObject traceObj = jsonArray.getJSONObject(i);
                deliverTrace = new DeliverTrace();
                if (!traceObj.isNull("AcceptTime")) {
                    deliverTrace.setAcceptTime(traceObj.optString("AcceptTime"));
                }

                if (!traceObj.isNull("AcceptStation")) {
                    deliverTrace.setAcceptStation(traceObj.optString("AcceptStation"));
                }

                if (!traceObj.isNull("Remark")) {
                    deliverTrace.setRemark(traceObj.optString("Remark"));
                }
                deliverTraces.add(deliverTrace);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return deliverTraces;
    }
}
