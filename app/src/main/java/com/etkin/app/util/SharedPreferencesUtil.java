package com.etkin.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtil {

    private static String PREF_NAME = "cozuyooapp";
    private static String PREF_KEY_JWT = "myjwt";
    public static String PREF_VALUE_JWT_DEFAULT = "a.a.a";


    public static void jwtSave(Context mContext, String jwtValue) {
        SharedPreferences spSettings =  mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        Editor spEditor = spSettings.edit();
        spEditor.putString(PREF_KEY_JWT,jwtValue);
        spEditor.apply();
    }

    public static String jwtGet(Context mContext) {
        SharedPreferences spSettings = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return spSettings.getString(PREF_KEY_JWT, PREF_VALUE_JWT_DEFAULT);
    }

    public static void jwtClear(Context mContext) {
        SharedPreferences spSettings =  mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        Editor spEditor = spSettings.edit();
        spEditor.remove(PREF_KEY_JWT);
        spEditor.apply();
    }

    public static String bearerHeader(Context mContext)  {
        String jwt = jwtGet(mContext);
        return "Bearer "+ jwt;
    }
}
