package com.kepoyah.gaide_ai.activity;

import static maes.tech.intentanim.CustomIntent.customType;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.kepoyah.gaide_ai.R;

public class Privacy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_ai);
        WebView webView = findViewById(R.id.webview);
        webView.loadUrl("file:///android_asset/p.html");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        customType(Privacy.this,"fadein-to-fadeout");
    }
}