package com.kepoyah.gaide_ai.ads.admob;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.kepoyah.gaide_ai.config.SettingsAi;
import com.zayviusdigital.artificialintelligence.listener.OnListener;

public class AdBannerAi {
    private  static AdView adView;

    public static void GET(Activity context, RelativeLayout frameLayout, OnListener onListener) {
        adView = new AdView(context);
        adView.setAdUnitId(SettingsAi.ad_banner_ai);
        frameLayout.addView(adView);
        loadBanner(context,onListener);

    }

    private static void loadBanner(Activity activity,  OnListener onListener) {
        AdRequest adRequest = new AdRequest.Builder().build();
        AdSize adSize = getAdSize(activity);
        adView.setAdSize(adSize);
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                onListener.failed();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
                onListener.succeed();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });
    }

    public static void destroy(){
        if (adView!=null){
            adView.destroy();
        }
    }

    private static AdSize getAdSize(Activity context) {
        Display display = context.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth);
    }
}
