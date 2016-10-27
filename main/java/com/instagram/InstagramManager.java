package com.ogoons.instagram;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;

import com.ogoons.instagram.model.InstagramMedia;
import com.ogoons.instagram.model.InstagramUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ogoons on 2016-09-20.
 */
public class InstagramManager implements InstagramDialog.InstagramDialogListener {
    private final String AUTH_BASE_URL  = "https://api.instagram.com/oauth/authorize/?";
    private final String GRANT_TYPE     = "authorization_code";

    private Context mContext;

    private InstagramDialog         mDialog;
    private InstagramSession        mSession;

    private String mClientId;
    private String mClientSecret;
    private String mRedirectUri;

    private Retrofit            mRetrofit;
    private InstagramRequest    mInstagramRequest;

    private static InstagramManager mInstance; // Singletone

    private InstagramImageListener mImageListener;
    private InstagramLoginListener mLoginListener;

    public InstagramManager() {}

    public InstagramManager(Context context, String clientId, String clientSecret, String redirectUrl) {
        mContext        = context;
        mClientId       = clientId;
        mClientSecret   = clientSecret;
        mRedirectUri    = redirectUrl;

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(InstagramRequest.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        mInstagramRequest = mRetrofit.create(InstagramRequest.class);
        mSession = new InstagramSession(context);
    }

    public static InstagramManager getInstance(Context context, String clientId, String clientSecret, String redirectUrl) {
        if (null == mInstance)
            mInstance = new InstagramManager(context, clientId, clientSecret, redirectUrl);
        return mInstance;
    }

    public static InstagramManager getInstance() {
        if (null == mInstance)
            return null;
        return mInstance;
    }

    public void login(final InstagramLoginListener loginListener) {
        mLoginListener = loginListener;
        String authUrl = AUTH_BASE_URL + "client_id=" + mClientId + "&redirect_uri=" + mRedirectUri + "&response_type=code";
        mDialog = new InstagramDialog(mContext, authUrl, mRedirectUri, this);
        mDialog.show();
    }

    public void logout() {
        mSession.clear();
    }

    public boolean isLogged() {
        return mSession.isActive();
    }

    public InstagramUser getUser() {
        return mSession.getUser();
    }

    public void requestMedia(final InstagramMediaListener listener) {
        if (null == mSession.getUser())
            return;

        String token = mSession.getUser().accessToken;
        mInstagramRequest.requestMedia(token, -1).enqueue(new Callback<InstagramMedia>() {
            @Override
            public void onResponse(Call<InstagramMedia> call, Response<InstagramMedia> response) {
                InstagramMedia medias = response.body();
                listener.onSuccess(medias);
            }

            @Override
            public void onFailure(Call<InstagramMedia> call, Throwable t) {
                listener.onFailure();
            }
        });
    }

    public void getMedia(final InstagramImageListener imageListener) {
        mImageListener = imageListener;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            new GetMediaAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mSession.getUser().accessToken);
        else
            new GetMediaAsyncTask().execute(mSession.getUser().accessToken);
    }

    public void requestAccessToken(String code) {
        mInstagramRequest.accessToken(mClientId, mClientSecret, GRANT_TYPE, mRedirectUri, code).enqueue(new Callback<InstagramAccessToken>() {
            @Override
            public void onResponse(Call<InstagramAccessToken> call, Response<InstagramAccessToken> response) {
                InstagramAccessToken accessToken = response.body();

                // 세션 정보 저장
                mSession.save(new InstagramUser(
                        accessToken.access_token,
                        accessToken.user.id,
                        accessToken.user.username,
                        accessToken.user.full_name,
                        accessToken.user.profile_picture));

                mLoginListener.onSuccess();
            }

            @Override
            public void onFailure(Call<InstagramAccessToken> call, Throwable t) {
            }
        });
    }

    @Override
    public void onSuccess(String code) {
        requestAccessToken(code);
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(String error) {

    }

    public class InstagramImage {
        public String url;
        public String width;
        public String height;

        public InstagramImage(String url, String width, String height) {
            this.url    = url;
            this.width  = width;
            this.height = height;
        }
    }

    private class GetMediaAsyncTask extends AsyncTask<String, Void, ArrayList<InstagramImage>> {
        @Override
        protected ArrayList<InstagramImage> doInBackground(String... params) {
            ArrayList<InstagramImage> instagramImages = new ArrayList<InstagramImage>();
            String token = params[0];

            try {
                InstagramMedia medias = mInstagramRequest.requestMedia(token, -1).execute().body();
                String maxId = medias.pagination.next_max_id;

                for (InstagramMedia.Data data : medias.data) {
                    instagramImages.add(new InstagramImage(
                            data.images.standard_resolution.url,
                            data.images.standard_resolution.width,
                            data.images.standard_resolution.height));
                }

                while (!TextUtils.isEmpty(maxId)) {
                    medias = mInstagramRequest.requestMediaNext(token, maxId).execute().body();
                    maxId = medias.pagination.next_max_id;

                    for (InstagramMedia.Data data : medias.data) {
                        instagramImages.add(new InstagramImage(
                                data.images.standard_resolution.url,
                                data.images.standard_resolution.width,
                                data.images.standard_resolution.height));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return instagramImages;
        }

        @Override
        protected void onPostExecute(ArrayList<InstagramImage> instagramImages) {
            super.onPostExecute(instagramImages);

            if (instagramImages.isEmpty()) {
                mImageListener.onFailure();
                return;
            }

            mImageListener.onSuccess(instagramImages);
        }
    }

    public interface InstagramUserInfoListener {
        void onSuccess(InstagramUser user);
        void onFailure();
    }

    public interface InstagramImageListener {
        void onSuccess(ArrayList<InstagramImage> images);
        void onFailure();
    }

    public interface InstagramMediaListener {
        void onSuccess(InstagramMedia medias);
        void onFailure();
    }

    public interface InstagramLoginListener {
        void onSuccess();
        void onFailure();
    }
}
