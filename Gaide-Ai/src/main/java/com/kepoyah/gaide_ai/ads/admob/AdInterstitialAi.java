package com.kepoyah.gaide_ai.ads.admob;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.kepoyah.gaide_ai.config.SettingsAi;
import com.zayviusdigital.artificialintelligence.dialog.DialogWait;
import com.zayviusdigital.artificialintelligence.listener.OnListener;

public class AdInterstitialAi {
    private static InterstitialAd mInterstitialAd;
    private static boolean loadingIklan=true;
    private static Integer hitung=0;
    @SuppressLint("StaticFieldLeak")
    private static DialogWait dialogWait;

    public static void SHOW(Activity activity,Integer position, OnListener onListener) {
        hitung++;
        if (loadingIklan) {
            loadingIklan = false;
        }
        if (hitung % SettingsAi.interval_inter_ai == 0) {
            dialogWait = new DialogWait(activity);
            dialogWait.show("Loading Ads....");
            SettingsAi.position_detail_ai = position;
            InterstitialAd.load(activity, SettingsAi.ad_interstitial_ai,
                    new AdRequest.Builder().build(),
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            mInterstitialAd = interstitialAd;
                            try {
                                new Handler().postDelayed(() -> {
                                    try {
                                        dialogWait.dismiss();
                                        if (mInterstitialAd != null) {
                                            mInterstitialAd.show(activity);
                                        }
                                        else {
                                            onListener.failed();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }, 1000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            interstitialAd.setFullScreenContentCallback(
                                    new FullScreenContentCallback() {
                                        @Override
                                        public void onAdDismissedFullScreenContent() {
                                            mInterstitialAd = null;
                                            onListener.succeed();
                                        }


                                        @Override
                                        public void onAdShowedFullScreenContent() {

                                        }
                                    });

                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            // Handle the error
                            mInterstitialAd = null;
                            onListener.failed();
                            dialogWait.dismiss();

                        }
                    });
            loadingIklan = true;
        }else {
            onListener.load();
        }
    }
}
