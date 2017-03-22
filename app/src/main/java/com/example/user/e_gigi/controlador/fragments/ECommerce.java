package com.example.user.e_gigi.controlador.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.user.e_gigi.R;

public class ECommerce extends Fragment {

private WebView webView;
String url = "http://www.e-gigi.com/shop/index.php";


    public ECommerce() {
        // Required empty public constructor
    }

    public static ECommerce newInstance() {
        ECommerce fragment = new ECommerce();
       fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fm = getFragmentManager();
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (webView.canGoBack())
                    webView.goBack();
                else
                getActivity().onBackPressed();
            }
        });
    }
    private class MyWebViewClient extends WebViewClient {


        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals("http://www.e-gigi.com/shop/index.php")) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                view.getContext().startActivity(intent);
                return true;
            }else {
                return super.shouldOverrideUrlLoading(view, url);
            }
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            Toast toast = Toast.makeText(getActivity(),"Loading...", Toast.LENGTH_SHORT);
            toast.show();
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_ecommerce, container, false);

        webView=(WebView)v.findViewById(R.id.web_view);
       // WebSettings webSettings = webView.getSettings();
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDomStorageEnabled(true);

        webView.loadUrl(url);

        return v;
    }

}