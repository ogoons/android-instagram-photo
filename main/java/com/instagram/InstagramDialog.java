package com.zzixx.instagram;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.zzixx.R;

/**
 * Created by ogoons on 2016-09-20.
 */
public class InstagramDialog extends Dialog {
    private ProgressBar     mPBLoading;

    private String mAuthUrl;
    private String mRedirectUri;

    private WebView mWebView;
    private InstagramDialogListener mListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupDialog();
        setupWebView();

        mPBLoading = (ProgressBar) findViewById(R.id.pb_loading);
    }

    public InstagramDialog(Context context, String authUrl, String redirectUri, InstagramDialogListener listener) {
        super(context);

        mAuthUrl = authUrl;
        mRedirectUri = redirectUri;
        mListener = listener;
    }

    private void progressBarVisible(boolean visible) {
        if (visible) {
            mWebView.setVisibility(View.INVISIBLE);
            mPBLoading.setVisibility(View.VISIBLE);
        } else {
            mWebView.setVisibility(View.VISIBLE);
            mPBLoading.setVisibility(View.INVISIBLE);
        }
    }

    private void setupDialog() {
        /*
        DisplayMetrics metrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        final int width = metrics.widthPixels;
        final int height = metrics.heightPixels;

        double[] dimensions = new double[2];
        if (width < height) {
            dimensions[0] = 0.87 * width;
            dimensions[1] = 0.82 * height;
        } else {
            dimensions[0] = 0.75 * width;
            dimensions[1] = 0.75 * height;
        }
        */

        View view = (View) getLayoutInflater().inflate(R.layout.dialog_instagram, null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(view, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
    }

    private void setupWebView() {
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setWebViewClient(new InstagramWebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSaveFormData(false);
        mWebView.loadUrl(mAuthUrl);
    }

    public void clearCache() {
        mWebView.clearCache(true);
        mWebView.clearHistory();
        mWebView.clearFormData();
    }

    private class InstagramWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            overrideUrlLoading(url);

            return true;
        }

        void overrideUrlLoading(String url) {
            if (url.startsWith(mRedirectUri)) {
                if (url.contains("code")) {
                    String temp[] = url.split("=");

                    mListener.onSuccess(temp[1]);
                } else if (url.contains("error")) {
                    String temp[] = url.split("=");

                    mListener.onError(temp[temp.length-1]);
                }

                // 성공이던 실패던 다이얼로그 닫기
                InstagramDialog.this.dismiss();
            }
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            progressBarVisible(true);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            progressBarVisible(false);
        }
    }

    public interface InstagramDialogListener {
        void onSuccess(String code);
        void onCancel();
        void onError(String error);
    }
}
