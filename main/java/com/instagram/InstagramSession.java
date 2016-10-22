package com.zzixx.instagram;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;

import com.zzixx.ZXApplication;
import com.zzixx.instagram.model.InstagramUser;

/**
 * Created by ogoons on 2016-09-21.
 */
public class InstagramSession {
    private Context mContext;
    private SharedPreferences mSharedPref;

    private static final String SHARED = "instagram_pref";
    private static final String USER_ID = "user_id";
    private static final String USER_NAME = "user_name";
    private static final String FULL_NAME = "full_name";
    private static final String PROFILE_PIC = "profile_pic";
    private static final String ACCESS_TOKEN = "access_token";

    public InstagramSession(Context context) {
        mContext = context;
        mSharedPref = context.getSharedPreferences(SHARED, Context.MODE_PRIVATE);
    }

    public void save(InstagramUser user) {
        Editor editor = mSharedPref.edit();
        editor.putString(ACCESS_TOKEN, user.accessToken);
        editor.putString(USER_ID, user.id);
        editor.putString(USER_NAME, user.userName);
        editor.putString(FULL_NAME, user.fullName);
        editor.putString(PROFILE_PIC, user.profilePic);
        editor.commit();
    }

    public void clear() {
        Editor editor = mSharedPref.edit();
        editor.clear();
        editor.commit();

        if (Build.VERSION_CODES.LOLLIPOP > Build.VERSION.SDK_INT) {
            CookieSyncManager.createInstance(mContext);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
        } else {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
                @Override
                public void onReceiveValue(Boolean aBoolean) {
                    Log.d(ZXApplication.LOG_TAG, "removeAllCookies > onReceiveValue");
                }
            });

        }

        /*
        CookieSyncManager.createInstance(mContext);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        */
    }

    public InstagramUser getUser() {
        if (false == mSharedPref.contains(ACCESS_TOKEN))
            return null;

        String accessToken = mSharedPref.getString(ACCESS_TOKEN, "");
        String userId = mSharedPref.getString(USER_ID, "");
        String userName = mSharedPref.getString(USER_NAME, "");
        String fullName = mSharedPref.getString(FULL_NAME, "");
        String profilePic = mSharedPref.getString(PROFILE_PIC, "");

        return new InstagramUser(accessToken, userId, userName, fullName, profilePic);
    }

    public boolean isActive() {
        return mSharedPref.contains(ACCESS_TOKEN);
    }

}
