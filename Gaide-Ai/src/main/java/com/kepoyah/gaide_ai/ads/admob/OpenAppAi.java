package com.kepoyah.gaide_ai.ads.admob;

import static androidx.lifecycle.Lifecycle.Event.ON_START;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.kepoyah.gaide_ai.config.SettingsAi;

public class OpenAppAi implements Application.ActivityLifecycleCallbacks, LifecycleObserver {
    private static final String LOG_TAG = "OpenAppAi";
    private static AppOpenAd appOpenAd = null;
    private static AppOpenAd.AppOpenAdLoadCallback loadCallback;
    @SuppressLint("StaticFieldLeak")
    private static Application myApplication;
    @SuppressLint("StaticFieldLeak")
    private static Activity currentActivity;
    private static boolean isShowingAd = false;

    /** Constructor */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public OpenAppAi(Application myApplication) {
        OpenAppAi.myApplication = myApplication;
        OpenAppAi.myApplication.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    /** Request an ad */
    private static void fetchAd() {
        // We will implement this below.
        // Have unused ad, no need to fetch another.
        if (isAdAvailable()) {
            return;
        }

        /**
         * Called when an app open ad has loaded.
         *
         * @param ad the loaded app open ad.
         */
        /**
         * Called when an app open ad has failed to load.
         *
         * @param loadAdError the error.
         */
        // Handle the error.
        loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {

            @Override
            public void onAdLoaded(@NonNull AppOpenAd ad) {
                appOpenAd = ad;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error.
            }

        };
        AdRequest request = getAdRequest();
        AppOpenAd.load(myApplication, SettingsAi.ad_open_ai,
                request, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                loadCallback);

    }

    /** Creates and returns ad request. */
    private static AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    /** Utility method that checks if ad exists and can be shown. */
    private static boolean isAdAvailable() {
        return appOpenAd != null;
    }

    /** ActivityLifecycleCallback methods */
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}

    @Override
    public void onActivityStarted(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityStopped(Activity activity) {}

    @Override
    public void onActivityPaused(Activity activity) {}

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {}

    @Override
    public void onActivityDestroyed(Activity activity) {
        currentActivity = null;
    }

    public static void showAdIfAvailable() {
        if (!isShowingAd && isAdAvailable()) {
            Log.d(LOG_TAG, "Will show ad.");

            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Set the reference to null so isAdAvailable() returns false.
                            appOpenAd = null;
                            isShowingAd = false;
                            fetchAd();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {}

                        @Override
                        public void onAdShowedFullScreenContent() {
                            isShowingAd = true;

                        }
                    };

            appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
            appOpenAd.show(currentActivity);

        } else {
            Log.d(LOG_TAG, "Can not show ad.");
            fetchAd();
        }
    }

    /** LifecycleObserver methods */
    @OnLifecycleEvent(ON_START)
    public void onStart() {
        showAdIfAvailable();
        Log.d(LOG_TAG, "onStart");
    }
}
