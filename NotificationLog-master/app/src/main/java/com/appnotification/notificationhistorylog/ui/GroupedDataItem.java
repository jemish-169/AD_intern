package com.appnotification.notificationhistorylog.ui;

import android.content.Context;


import com.appnotification.notificationhistorylog.misc.Const;
import com.appnotification.notificationhistorylog.misc.Util;

import org.json.JSONException;
import org.json.JSONObject;

public class GroupedDataItem {
    private long id;
    private String packageName;
    private String appName;
    private String text;
    private String preview;

    GroupedDataItem(Context context, String str){
        try{
            JSONObject json = new JSONObject(str);
            packageName = json.getString("packageName");
            appName = Util.getAppNameFromPackage(context, packageName, false);
            text = str;

            String title = json.optString("title");
            String text = json.optString("text");
            preview = (title + "\n" + text).trim();
        }catch (JSONException e){
            if(Const.DEBUG) e.printStackTrace();
        }
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }
}
