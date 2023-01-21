package com.kepoyah.gaide_ai.ads.admob;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.UserMessagingPlatform;
import com.zayviusdigital.artificialintelligence.listener.OnListener;

public class AdInitializeAi {
    private static ConsentInformation consentInformation;

    public static void SDK(Context activity, OnListener listener){
        MobileAds.initialize(activity, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                listener.succeed();
            }
        });
    }

    public static void GDPR(Context activity){
        ConsentRequestParameters params = new ConsentRequestParameters
                .Builder()
                .setTagForUnderAgeOfConsent(false)
                .build();
        consentInformation = UserMessagingPlatform.getConsentInformation(activity);
        consentInformation.requestConsentInfoUpdate(
                (Activity) activity,
                params,
                () -> {
                    if (consentInformation.isConsentFormAvailable()) {
                        loadForm(activity);
                    }
                },
                formError -> {

                });
    }

    private static void loadForm(Context activity){
        UserMessagingPlatform.loadConsentForm(
                activity,
                consentForm -> {
                    if(consentInformation.getConsentStatus() == ConsentInformation.ConsentStatus.REQUIRED) {
                        consentForm.show(
                                (Activity) activity,
                                formError -> {
                                    // Handle dismissal by reloading form.
                                    loadForm(activity);
                                });

                    }

                },
                formError -> {

                }
        );
    }
}
