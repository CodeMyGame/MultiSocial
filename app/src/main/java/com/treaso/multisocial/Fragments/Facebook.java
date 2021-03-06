package com.treaso.multisocial.Fragments;


import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.treaso.multisocial.R;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Facebook extends Fragment {

    public static WebView webView;
    SmoothProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;

    public Facebook() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_facebook, container, false);
        progressBar = (SmoothProgressBar) view.findViewById(R.id.progressBar);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#3b5998"));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.loadUrl("http://www.facebook.com");
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        webView = (WebView) view.findViewById(R.id.webView);
        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        webViewSettings();
        webView.loadUrl("http://www.facebook.com");


        return view;
    }

    private void webViewSettings() {


        webView.setWebViewClient(new WebViewClient());  //to open new link in same web application
        webView.getSettings().setJavaScriptEnabled(true);  //some sites uses java_scripts like youtube.com
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setDisplayZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        webView.requestFocus();
        registerForContextMenu(webView);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.requestFocusFromTouch();
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress < 100 && progressBar.getVisibility() == (ProgressBar.GONE)) {
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
                if (newProgress == 100 && progressBar.getVisibility() == (ProgressBar.VISIBLE)) {
                    progressBar.setVisibility(ProgressBar.GONE);
                }

            }


        });
        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                webView.loadUrl("file:///android_asset/feedback.html");

            }
        });

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                try {
                    Toast.makeText(getActivity(), "Downloading....", Toast.LENGTH_LONG).show();
                    String file = getfilename(url);
                    Uri source = Uri.parse(url);
                    DownloadManager.Request req = new DownloadManager.Request(source);
                    req.setDescription(url);
                    req.setTitle(file);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        req.allowScanningByMediaScanner();
                    }
                    req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    req.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, url);
                    DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                    manager.enqueue(req);


                } catch (Exception e) {
                    Toast.makeText(getActivity(), "ERROR : " + e, Toast.LENGTH_LONG).show();
                }

            }
        });


    }


    private String getfilename(String downloadurl) {
        try {
            String filename = "";
            int countslash = 0;

            int length = downloadurl.length();
            for (int i = 0; i < length; i++) {
                if (downloadurl.charAt(i) == '/') {
                    countslash++;
                }
            }
            int temp = 0;
            int j = 0;
            while (temp != countslash) {
                if (downloadurl.charAt(j) == '/') {
                    temp++;
                }
                j++;
            }
            StringBuilder sb = new StringBuilder(downloadurl);
            filename = sb.delete(0, j).toString();
            return filename;
        } catch (Exception e) {
            Toast.makeText(getActivity(), "ERROR : " + e, Toast.LENGTH_LONG).show();
        }
        return null;
    }

}
