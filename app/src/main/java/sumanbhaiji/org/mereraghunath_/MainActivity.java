package sumanbhaiji.org.mereraghunath_;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.iid.FirebaseInstanceId;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnHanumanChalisa;
    private Button btnRamCharitManas;
    private Button btnRamRakshaStotra;
    private Button btnYoutube;
    private Button btnFacebook;
    //private Button btnWhatsapp;
    private Button btnTwitter;
    private InterstitialAd mInterstitialAd;
    private int ID =0;
    private String tokenId=null;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tokenId  = FirebaseInstanceId.getInstance().getToken();
       // Log.d("TOKEN",tokenId);
        init();
        initListener();
        createBannerAd();

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                if(ID != 0)
                startWebActivity(ID);
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
        btnHanumanChalisa = (Button) findViewById(R.id.btnHanumanChalisa);
        btnRamCharitManas = (Button) findViewById(R.id.btnRamCharitManas);
        btnRamRakshaStotra = (Button) findViewById(R.id.btnRamRakshaStotra);
        btnYoutube = (Button) findViewById(R.id.btnYoutube);
        btnFacebook = (Button) findViewById(R.id.btnFacebook);
       // btnWhatsapp = (Button) findViewById(R.id.btnWhatsapp);
        btnTwitter = (Button) findViewById(R.id.btnTwitter);
    }
    private void initListener() {
        btnHanumanChalisa.setOnClickListener(this);
        btnRamCharitManas.setOnClickListener(this);
        btnRamRakshaStotra.setOnClickListener(this);
        btnYoutube.setOnClickListener(this);
        btnFacebook.setOnClickListener(this);
       // btnWhatsapp.setOnClickListener(this);
        btnTwitter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnHanumanChalisa:
                Intent hanumanKand = new Intent(MainActivity.this, KandActivity.class);
                hanumanKand.putExtra("ID","HANUMAN_CHALISA");
                startActivity(hanumanKand);
                break;
            case R.id.btnRamCharitManas:
                Intent intent = new Intent(MainActivity.this, RamCharitManasActivity.class);
                startActivity(intent);
                break;
            case R.id.btnRamRakshaStotra:
                Intent rakshaKand = new Intent(MainActivity.this, KandActivity.class);
                rakshaKand.putExtra("ID","RAM_RAKSHA_STOTRA");
                startActivity(rakshaKand);
                break;
            case R.id.btnYoutube:
                startWebActivity(R.id.btnYoutube);
                break;
            case R.id.btnFacebook:
                startWebActivity(R.id.btnFacebook);
                break;
            case R.id.btnTwitter:
                startWebActivity(R.id.btnTwitter);
                break;
            default:
                break;
        }

        // buttonclicked(v);
        // case R.id.btnWhatsapp:
        // startWebActivity(R.id.btnWhatsapp);
        // break;

    }

//    private void buttonclicked(View view) {
//        kandId = getResources().getResourceEntryName(view.getId());
//        if(mInterstitialAd.isLoaded())
//        {
//            mInterstitialAd.show();
//        } else {
//            startKandActivity();
//        }
//    }

    private void startWebActivity(int socialMediaId) {
        if(mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            ID = socialMediaId;
        } else {
            Intent intent = new Intent(MainActivity.this, WebActivity.class);
            intent.putExtra("SOCIAL_MEDIA_ID", socialMediaId);
            startActivity(intent);
        }
    }


}
