package me.jaxvy.boilerplate.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Date;

public class SharedPrefs {

    private static final String AUTH_TOKEN = "auth_token";
    private static final String AUTH_TOKEN_TIME_CREATE = "auth_token_time_create";

    private final SharedPreferences mSharedPreferences;

    public SharedPrefs(Context context) {
        mSharedPreferences = context.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
    }

    public void setAuthToken(String authToken) {
        Log.d("AUTH_TOKEN", authToken);
        mSharedPreferences.edit()
                .putLong(AUTH_TOKEN_TIME_CREATE, new Date().getTime())
                .apply();
        mSharedPreferences.edit()
                .putString(AUTH_TOKEN, authToken)
                .apply();
    }

    public String getAuthToken() {
        return mSharedPreferences.getString(AUTH_TOKEN, null);
    }

    public long getAuthTokenTimeCreate() {
        return mSharedPreferences.getLong(AUTH_TOKEN_TIME_CREATE, -1);
    }

    public void clearAuthToken() {
        mSharedPreferences.edit()
                .remove(AUTH_TOKEN)
                .apply();

        mSharedPreferences.edit()
                .remove(AUTH_TOKEN_TIME_CREATE)
                .apply();
    }
}
