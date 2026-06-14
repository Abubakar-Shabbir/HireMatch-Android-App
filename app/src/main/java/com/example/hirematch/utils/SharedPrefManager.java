package com.example.hirematch.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static final String PREF_NAME = "HireMatch";

    private static final String KEY_UID = "uid";
    private static final String KEY_ROLE = "role";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {

        preferences =
                context.getSharedPreferences(
                        PREF_NAME,
                        Context.MODE_PRIVATE
                );

        editor = preferences.edit();
    }

    public void saveUser(String uid, String role) {

        editor.putString(KEY_UID, uid);
        editor.putString(KEY_ROLE, role);
        editor.apply();
    }

    public String getUid() {
        return preferences.getString(KEY_UID, "");
    }

    public String getRole() {
        return preferences.getString(KEY_ROLE, "");
    }

    public boolean isLoggedIn() {
        return !getUid().isEmpty();
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}