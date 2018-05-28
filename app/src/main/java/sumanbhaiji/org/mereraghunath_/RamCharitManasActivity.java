package sumanbhaiji.org.mereraghunath_;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by jigneshmodi on 10/02/18.
 */

public class RamCharitManasActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonA;
    private Button buttonB;
    private Button buttonC;
    private Button buttonD;
    private Button buttonE;
    private Button buttonF;
    private Button buttonG;
    private String kandId = null;
    private InterstitialAd mInterstitialAd;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ramcharitmanas);
        init();
        initListener();
        createBannerAd();

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                startKandActivity();
                mInterstitialAd.loadAd(new AdRequest.Builder().build());

            }
        });
    }

    private void createBannerAd(){
        MobileAds.initialize(this,getResources().getString(R.string.admob_app_id));
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


    private void init() {
        buttonA = (Button) findViewById(R.id.A);
        buttonB = (Button) findViewById(R.id.B);
        buttonC = (Button) findViewById(R.id.C);
        buttonD = (Button) findViewById(R.id.D);
        buttonE = (Button) findViewById(R.id.E);
        buttonF = (Button) findViewById(R.id.F);
        buttonG = (Button) findViewById(R.id.G);
    }
    private void initListener() {
        buttonA.setOnClickListener(this);
        buttonB.setOnClickListener(this);
        buttonC.setOnClickListener(this);
        buttonD.setOnClickListener(this);
        buttonE.setOnClickListener(this);
        buttonF.setOnClickListener(this);
        buttonG.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        buttonclicked(v);
    }

    private void buttonclicked(View view) {
        kandId = getResources().getResourceEntryName(view.getId());
        if(mInterstitialAd.isLoaded())
        {
            mInterstitialAd.show();
        } else {
            startKandActivity();
        }
    }

    private void startKandActivity() {
        Intent intent = new Intent(RamCharitManasActivity.this, KandActivity.class);
        intent.putExtra("ID", kandId);
        startActivity(intent);
    }
}

