package com.kepoyah.gaide_ai.activity;

import static maes.tech.intentanim.CustomIntent.customType;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.kepoyah.gaide_ai.R;
import com.kepoyah.gaide_ai.ads.admob.AdInitializeAi;
import com.kepoyah.gaide_ai.ads.admob.AdNativeAi;
import com.kepoyah.gaide_ai.config.SettingsAi;
import com.zayviusdigital.artificialintelligence.dialog.DialogWait;
import com.zayviusdigital.artificialintelligence.listener.OnListener;

public class Home extends AppCompatActivity {
    private FrameLayout frameLayout;
    private CardView cardView;
    private DialogWait dialogWait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_ai);
        MaterialToolbar materialToolbar = findViewById(R.id.toolbar);
        materialToolbar.setTitle(SettingsAi.app_name);
        frameLayout =  findViewById(R.id.native_ad);
        cardView = findViewById(R.id.native_ad_car);
        dialogWait = new DialogWait(this);
        dialogWait.show("Please wait");
        AdInitializeAi.GDPR(this);
        AdInitializeAi.SDK(this, new OnListener() {
            @Override
            public void succeed() {
                super.succeed();
                AdNativeAi.GET(Home.this, frameLayout, new OnListener() {
                    @Override
                    public void succeed() {
                        super.succeed();
                        dialogWait.dismiss();
                        cardView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void failed() {
                        super.failed();
                        cardView.setVisibility(View.GONE);
                        dialogWait.dismiss();
                    }
                });
            }
        });

        findViewById(R.id.pp).setOnClickListener(view -> {
            Intent intent = new Intent(this, Privacy.class);
            startActivity(intent);
            customType(Home.this,"fadein-to-fadeout");
        });
        findViewById(R.id.bt_open).setOnClickListener(view ->{
            go_page();
        });
    }

    private void go_page(){
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
        customType(Home.this,"fadein-to-fadeout");
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Are you sure you want to quit this application?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
           finish();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }
}