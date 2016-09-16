package com.rkhaut.posted.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {

    private static final String TAG = JSONParser.class.getName();

    public static Boolean getBoolean(JSONObject response, String key, boolean defaultValue) {
        Boolean result = defaultValue;
        try {
            if(response.has(key)) {
                result = response.getBoolean(key);
            }
        } catch (JSONException e) {
            Log.v(TAG, e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public static String getString(JSONObject response, String key, String defaultValue) {
        String result = defaultValue;
        try {
            if(response.has(key)) {
                result = response.getString(key);
            }
        } catch (JSONException e) {
            Log.v(TAG, e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public static Integer getInt(JSONObject response, String key, int defaultValue) {
        Integer result = defaultValue;
        try {
            if(response.has(key)) {
                result = response.getInt(key);
            }
        } catch (JSONException e) {
            Log.v(TAG, e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
