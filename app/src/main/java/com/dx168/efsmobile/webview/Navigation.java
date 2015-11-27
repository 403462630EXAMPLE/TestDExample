package com.dx168.efsmobile.webview;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Bruce on 2/2/15.
 */
public class Navigation {
    public NavigationPlace type;
    public JSONObject data;

    public static Navigation fromJson(String json) {
        Navigation nav = new Navigation();
        try {
            JSONObject obj = new JSONObject(json);
            nav.type = NavigationPlace.fromValue(obj.getInt("type"));
            nav.data = obj.getJSONObject("detail");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return nav;
    }

    public String getDataJson() {
        return data.toString();
    }
}
