package com.example.mytrainer;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
public class BlankFragment extends Fragment {

    private TextView centerBoxTextView;
    private SharedViewModel sharedViewModel;
    public BlankFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        TextView pBar_Upper = view.findViewById(R.id.pBar_Upper);
        ProgressBar progressBar_upper = view.findViewById(R.id.progressbar_upper); // ProgressBar 찾기
        TextView pBar_Lower = view.findViewById(R.id.pBar_Lower);
        ProgressBar progressBar_lower = view.findViewById(R.id.progressbar_lower);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        WebView webView1 = view.findViewById(R.id.webview1);
        WebView webView2 = view.findViewById(R.id.webview2);
        WebView webView3 = view.findViewById(R.id.webview3);

        setupWebView(webView1, "https://www.youtube.com/watch?v=YOUR_VIDEO_ID_1");
        setupWebView(webView2, "https://www.youtube.com/watch?v=YOUR_VIDEO_ID_2");
        setupWebView(webView3, "https://www.youtube.com/watch?v=YOUR_VIDEO_ID_3");


        sharedViewModel.getUpperBodyCount().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s)
            {
                int upperCount = Integer.parseInt(s);
                progressBar_upper.setProgress(upperCount); // 진행 상태 업데이트

                if (upperCount < 30) {
                    // 예: Upper Count가 30보다 작으면 빨간색으로 설정
                    progressBar_upper.setProgressTintList(ColorStateList.valueOf(Color.RED));
                } else {
                    // 예: Upper Count가 30 이상이면 녹색으로 설정
                    progressBar_upper.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                }
                pBar_Upper.setText("Upper:"+s+"/100");
            }
        });

        sharedViewModel.getLowerBodyCount().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s)
            {

                int lowerCount = Integer.parseInt(s);
                progressBar_lower.setProgress(lowerCount);

                if(lowerCount < 30){
                    progressBar_lower.setProgressTintList(ColorStateList.valueOf(Color.RED));
                }
                else{
                    progressBar_lower.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                }
                pBar_Lower.setText("Lower:"+s+"/100");
            }
        });

        return view;
    }

    private void setupWebView(WebView webView, String url) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }
}