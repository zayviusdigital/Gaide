package com.kepoyah.gaide_ai.activity;


import static com.kepoyah.gaide_ai.config.SettingsAi.mode_ads;
import static maes.tech.intentanim.CustomIntent.customType;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.github.javiersantos.piracychecker.PiracyChecker;
import com.github.javiersantos.piracychecker.callbacks.PiracyCheckerCallback;
import com.github.javiersantos.piracychecker.enums.InstallerID;
import com.github.javiersantos.piracychecker.enums.PiracyCheckerError;
import com.github.javiersantos.piracychecker.enums.PirateApp;
import com.github.ybq.android.spinkit.SpinKitView;
import com.kepoyah.gaide_ai.R;
import com.kepoyah.gaide_ai.config.SettingsAi;
import com.zayviusdigital.artificialintelligence.ArtificialIntelligence;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Splash extends AppCompatActivity {

    private RelativeLayout sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_splash_ai);
        sp = findViewById(R.id.sp);
        SpinKitView load = findViewById(R.id.load);
        if (!(SettingsAi.color_all ==null)) {
            load.setColor(SettingsAi.color_all);
        }
        AndroidNetworking.initialize(getApplicationContext());
        getsp();

    }

    public void get_data() {
        new PiracyChecker(this)
                .enableInstallerId(InstallerID.GOOGLE_PLAY)
                .callback(new PiracyCheckerCallback() {
                    @Override
                    public void doNotAllow(@NonNull PiracyCheckerError piracyCheckerError, @Nullable PirateApp pirateApp) {
                        mode_ads =false;
                        getsp();
                    }

                    @Override
                    public void allow() {
                        mode_ads =true;
                        getsp();
                    }
                }).start();

    }



    public void getsp() {
        AndroidNetworking.get(ArtificialIntelligence.keys_encryption_url(ArtificialIntelligence.txt_from_asset(Splash.this,"log.txt")))
                .setPriority(Priority.HIGH)
                .doNotCacheResponse()
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        {
                            try {
                                if (response.getString("packagename_new_app").isEmpty()) {
                                    SettingsAi.database_ai = response.getString("database");
                                    SettingsAi.interval_inter_ai = response.getInt("interval_interstitial");
                                    if (mode_ads) {
                                        JSONArray jsonArray2 = response.getJSONArray("admob");
                                        for (int i = 0; i < jsonArray2.length(); i++) {
                                            JSONObject obj = jsonArray2.getJSONObject(i);
                                            SettingsAi.ad_banner_ai = obj.getString("banner");
                                            SettingsAi.ad_native_ai = obj.getString("native");
                                            SettingsAi.ad_interstitial_ai = obj.getString("interstitial");
                                        }
                                    }
                                    go_page();

                                }else {
                                    sp.setVisibility(View.GONE);
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Splash.this);
                                    builder.setCancelable(false);
                                    builder.setTitle("Update");
                                    builder.setMessage("A new version is available for this application.");
                                    builder.setPositiveButton("Update", (dialog, which) -> {
                                        Uri uri = null;
                                        try {
                                            uri = Uri.parse("market://details?id="+response.getString("packagename_new_app"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                                        try {
                                            startActivity(goToMarket);
                                        } catch (ActivityNotFoundException e) {
                                            try {
                                                startActivity(new Intent(Intent.ACTION_VIEW,
                                                        Uri.parse("http://play.google.com/store/apps/details?id="+response.getString("packagename_new_app"))));
                                            } catch (JSONException jsonException) {
                                                jsonException.printStackTrace();
                                            }
                                        }
                                    });
                                    builder.setNegativeButton("Exit", (dialog, which) -> finish());
                                    final AlertDialog alert = builder.create();
                                    alert.setOnShowListener(arg0 -> {
                                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));
                                        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.blue));
                                    });
                                    alert.show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(Splash.this, "The server is busy. Please try again later!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void go_page(){
        Intent intent = new Intent(getBaseContext(), Home.class);
        startActivity(intent);
        finish();
        customType(Splash.this, "fadein-to-fadeout");
    }
}