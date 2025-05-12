package com.androidlearning.admob;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.androidlearning.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AdmobActivity extends AppCompatActivity implements View.OnClickListener {

    private ExecutorService executor;
    private FrameLayout adViewContainer;
    private AdView adView;

    private InterstitialAd mInterstitialAd;
    private RewardedAd rewardedAd;

    private Button bannerLoad, bannerRelease, interstitialLoad, rewardLoad;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admob);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Or a fixed thread pool: Executors.newFixedThreadPool(2);
        executor = Executors.newSingleThreadExecutor();
        initializeMobileAdsSdk();

        bannerLoad = findViewById(R.id.admob_banner_load);
        bannerLoad.setOnClickListener(this);
        bannerRelease = findViewById(R.id.admob_banner_release);
        bannerRelease.setOnClickListener(this);
        interstitialLoad = findViewById(R.id.admob_interstitial_load);
        interstitialLoad.setOnClickListener(this);
        rewardLoad = findViewById(R.id.admob_reward_load);
        rewardLoad.setOnClickListener(this);

        //The initialization of interstitial and reward ads is usually done in the onCreate() method of an Activity
        initInterstitialAds();
        initRewardAds();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.admob_banner_load) {
            loadBannerAds();
        } else if (id == R.id.admob_banner_release) {
            releaseBannerAds();
        } else if (id == R.id.admob_interstitial_load) {
            loadInterstitialAds();
        }  else if (id == R.id.admob_reward_load) {
            loadRewardAds();
        }
    }

    private void initRewardAds() {
        AdRequest adRequest = new AdRequest.Builder().build();
        //google test reward ad unit id: ca-app-pub-3940256099942544/5224354917
        //own: ca-app-pub-4131003179451279/2939778085
        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d("Reward Ads", loadAdError.toString());
                        rewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd ad) {
                        rewardedAd = ad;
                        Log.d("Reward Ads", "Ad was loaded.");
                    }
                });
    }
    private void loadRewardAds() {
        if(rewardedAd == null) {
            Toast.makeText(this, "RewardAd is not ready yet.", Toast.LENGTH_SHORT).show();
            return;
        }

        rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d("Reward Ads", "Ad was clicked.");
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                Log.d("Reward Ads", "Ad dismissed fullscreen content.");
                rewardedAd = null;
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                // Called when ad fails to show.
                Log.e("Reward Ads", "Ad failed to show fullscreen content.");
                rewardedAd = null;
            }

            @Override
            public void onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d("Reward Ads", "Ad recorded an impression.");
            }

            @Override
            public void onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d("Reward Ads", "Ad showed fullscreen content.");
            }
        });

        //Show the ad and move to the RewardNextActivity
        rewardedAd.show(this, rewardItem -> {
            // Handle the reward.
            int rewardAmount = rewardItem.getAmount();
            String rewardType = rewardItem.getType();
            Log.d("Reward Ads", "The user earned: " + rewardAmount + " and type: " + rewardType);

            Intent intent = new Intent(this, RewardNextActivity.class);
            startActivity(intent);
        });
    }

    private void initInterstitialAds() {

        AdRequest adRequest = new AdRequest.Builder().build();
        //google test interstitial ad unit id: ca-app-pub-3940256099942544/1033173712
        //own: ca-app-pub-4131003179451279/3019196379
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i("Interstitial Ads", "onAdLoaded");
                    }
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d("Interstitial Ads", loadAdError.toString());
                        mInterstitialAd = null;
                    }

                });
    }

    //https://developers.google.com/admob/android/interstitial
    private void loadInterstitialAds() {
        if(mInterstitialAd == null) {
            Toast.makeText(this, "InterstitalAd is not ready yet.", Toast.LENGTH_SHORT).show();
            return;
        }
        /**
         * The FullScreenContentCallback handles events related to displaying your InterstitialAd.
         * Before showing InterstitialAd, make sure to set the callback:
         */
        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
            @Override
            public void onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d("Interstitial Ads", "Ad was clicked.");
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                Log.d("Interstitial Ads", "Ad dismissed fullscreen content.");
                mInterstitialAd = null;
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                // Called when ad fails to show.
                Log.e("Interstitial Ads", "Ad failed to show fullscreen content.");
                mInterstitialAd = null;
            }

            @Override
            public void onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d("Interstitial Ads", "Ad recorded an impression.");
            }

            @Override
            public void onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d("Interstitial Ads", "Ad showed fullscreen content.");
            }
        });

        /**
         * Show the ad
         * Interstitial ads should be displayed during natural pauses in the flow of an app.
         * Between levels of a game is a good example, or after the user completes a task.
         * To show an interstitial, use the show() method.
         */
        if (mInterstitialAd != null) {
            mInterstitialAd.show(this);
        } else {
            Log.d("Interstitial Ads", "The interstitial ad wasn't ready yet.");
        }
    }

    private void loadBannerAds() {
        adViewContainer = findViewById(R.id.admob_ad_view_container);
        // Create a new ad view.
        adView = new AdView(this);
        /**
         * google test ad unit id: ca-app-pub-3940256099942544/9214589741
         * replace with own ad unit id: ca-app-pub-4131003179451279/1199321004
         *
         * when use own id, it may not be displayed as first thing or it may not be displayed
         * directly since creating Admob ad units may take longer time for displaying real ads,
         * may take several days
         */
        adView.setAdUnitId("ca-app-pub-3940256099942544/9214589741");
        // Request an anchored adaptive banner with a width of 360.
        adView.setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, 360));

        // Replace ad container with new ad view.
        adViewContainer.removeAllViews();
        adViewContainer.addView(adView);

        //load ads
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private void releaseBannerAds() {
        // Remove banner from view hierarchy.
        if (adView.getParent() instanceof ViewGroup) {
            ((ViewGroup) adView.getParent()).removeView(adView);
        }
        // Destroy the banner ad resources.
        adView.destroy();
        // Drop reference to the banner ad.
        adView = null;
    }

    private void initializeMobileAdsSdk() {
        executor.execute(() -> {
            MobileAds.initialize(this, initializationStatus -> {
                // Optional: Handle initialization status if needed. For example:
                // Log.d("AdMob", "Initialization complete. Status: " + initializationStatus.getAdapterStatusMap());
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executor != null) {
            executor.shutdown();
        }
    }



    //    private void loadAds() {
//        adView = findViewById(R.id.admob_adView);
//        //load ads
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);
//
//        adView.setAdListener(new AdListener() {
//            @Override
//            public void onAdClicked() {
//                // Code to be executed when the user clicks on an ad.
//            }
//
//            @Override
//            public void onAdClosed() {
//                // Code to be executed when the user is about to return
//                // to the app after tapping on an ad.
//            }
//
//            @Override
//            public void onAdFailedToLoad(LoadAdError adError) {
//                // Code to be executed when an ad request fails.
//                // Handle the failure to load the ad, e.g.,
//                int errorCode = adError.getCode();
//                String errorMessage = adError.getMessage();
//                String domain = adError.getDomain();
//                Log.e("AdMob", "Ad failed to load: code=" + errorCode + ", message='" + errorMessage + "', domain='" + domain + "'");
//                //... (Similar error code explanations as in the Kotlin example) ...
//
//                //For a more user-friendly response (optional):
//                String userMessage = "Failed to load ad: " + errorMessage; // or a user-friendly message.
//                //You may also want to display something to the user (optional)
//                //Toast.makeText(context, userMessage, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAdImpression() {
//                // Code to be executed when an impression is recorded
//                // for an ad.
//            }
//
//            @Override
//            public void onAdLoaded() {
//                // Code to be executed when an ad finishes loading.
//            }
//
//            @Override
//            public void onAdOpened() {
//                // Code to be executed when an ad opens an overlay that
//                // covers the screen.
//            }
//        });
//    }

}