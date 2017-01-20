package com.kural.delivermanager.base;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * deliver order
 *
 */
public class DeliverOrder {


    private String EBusinessID;

    private boolean Success;

    private String LogisticCode;

    private String ShipperCode;

    private String ShipperName;


    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public String getLogisticCode() {
        return LogisticCode;
    }

    public void setLogisticCode(String logisticCode) {
        LogisticCode = logisticCode;
    }

    public String getShipperCode() {
        return ShipperCode;
    }

    public void setShipperCode(String shipperCode) {
        ShipperCode = shipperCode;
    }

    public String getShipperName() {
        return ShipperName;
    }

    public void setShipperName(String shipperName) {
        ShipperName = shipperName;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }


    public static DeliverOrder createFromJson(String orderStr) {

        DeliverOrder  deliverOrder = null;

        if (TextUtils.isEmpty(orderStr)) {
            return deliverOrder;
        }

        try {

            deliverOrder = new DeliverOrder();
            JSONObject  obj = new JSONObject(orderStr);

            if (!obj.isNull("EBusinessID")) {
                deliverOrder.setEBusinessID(obj.optString("EBusinessID"));
            }

            if (!obj.isNull("Success")) {
                deliverOrder.setSuccess(obj.optBoolean("Success"));
            }

            if (!obj.isNull("LogisticCode")) {
                deliverOrder.setLogisticCode(obj.optString("LogisticCode"));
            }

            if (!obj.isNull("Shippers")) {
                JSONArray jsonArray = obj.optJSONArray("Shippers");

                JSONObject shiperObj = jsonArray.getJSONObject(0);

                if (!shiperObj.isNull("ShipperCode")) {
                    deliverOrder.setShipperCode(shiperObj.optString("ShipperCode"));
                }

                if (!shiperObj.isNull("ShipperName")) {
                    deliverOrder.setShipperName(shiperObj.optString("ShipperName"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return deliverOrder;
    }
}
