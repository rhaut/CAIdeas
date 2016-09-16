package com.rkhaut.posted.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SessionManager {

    private static final String TAG = SessionManager.class.getName();

    private static final String NAME = "Posted";

    private static final String PREF_USERNAME = "username";
    private static final String PREF_TOKEN = "token";

    public static void saveSession(Context context, String username, String token) {
        Log.v(TAG, "Saving Session");
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_USERNAME, username);
        editor.putString(PREF_TOKEN, token);
        editor.apply();
    }

    public static void saveToken(Context context, String token) {
        Log.v(TAG, "Saving Token");
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_TOKEN, token);
        editor.apply();
    }

    public static void removeSession(Context context) {
        Log.v(TAG, "Invalid Session");
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(PREF_USERNAME);
        editor.remove(PREF_TOKEN);
        editor.apply();
    }

    public static String getUsername(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.getString(PREF_USERNAME, null);
    }

    public static String getToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.getString(PREF_TOKEN, null);
    }
}
