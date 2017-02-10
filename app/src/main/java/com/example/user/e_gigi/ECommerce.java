package com.example.user.e_gigi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class ECommerce extends Fragment {
public WebView webView;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_ecommerce, container, false);

        webView=(WebView)v.findViewById(R.id.web_view);
        webView.loadUrl("https://e-gigi.com");
        webView.setWebViewClient(new WebViewClient());

        return v;
    }

}
