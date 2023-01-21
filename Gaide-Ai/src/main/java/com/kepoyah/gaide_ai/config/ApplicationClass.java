package com.kepoyah.gaide_ai.config;


import static com.kepoyah.gaide_ai.config.SettingsAi.mode_ads;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.github.javiersantos.piracychecker.PiracyChecker;
import com.github.javiersantos.piracychecker.callbacks.PiracyCheckerCallback;
import com.github.javiersantos.piracychecker.enums.InstallerID;
import com.github.javiersantos.piracychecker.enums.PiracyCheckerError;
import com.github.javiersantos.piracychecker.enums.PirateApp;
import com.kepoyah.gaide_ai.ads.admob.AdInitializeAi;
import com.kepoyah.gaide_ai.ads.admob.OpenAppAi;
import com.zayviusdigital.artificialintelligence.ArtificialIntelligence;
import com.zayviusdigital.artificialintelligence.listener.OnListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApplicationClass extends Application {

    OpenAppAi openApp;

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
        GETJSON();

    }

    public void get_data() {
        new PiracyChecker(this)
                .enableInstallerId(InstallerID.GOOGLE_PLAY)
                .callback(new PiracyCheckerCallback() {
                    @Override
                    public void doNotAllow(@NonNull PiracyCheckerError piracyCheckerError, @Nullable PirateApp pirateApp) {
                        mode_ads =false;
                        GETJSON();
                    }

                    @Override
                    public void allow() {
                        mode_ads =true;
                        GETJSON();
                    }
                }).start();

    }

    public void GETJSON(){
        AndroidNetworking.get(ArtificialIntelligence.keys_encryption_url(ArtificialIntelligence.txt_from_asset(ApplicationClass.this,"log.txt")))
                .setPriority(Priority.HIGH)
                .doNotCacheResponse()
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        {
                            try {
                                if (mode_ads) {
                                    JSONArray jsonArray = response.getJSONArray("admob");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject obj = jsonArray.getJSONObject(i);
                                        SettingsAi.ad_open_ai = obj.getString("open_ad");
                                        AdInitializeAi.SDK(ApplicationClass.this, new OnListener() {
                                            @RequiresApi(api = Build.VERSION_CODES.Q)
                                            @Override
                                            public void succeed() {
                                                super.succeed();
                                                openApp = new OpenAppAi(ApplicationClass.this);

                                            }
                                        });
                                    }

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {

                    }
                });
    }
}