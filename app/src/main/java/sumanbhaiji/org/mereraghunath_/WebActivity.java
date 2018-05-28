package sumanbhaiji.org.mereraghunath_;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WebActivity extends AppCompatActivity implements View.OnClickListener{
    private WebView webView;
    private Button btnGoBack;
    private String url=null;
    private ProgressBar webProgressBar;
    private TextView loadingTV;
    private int socialMediaID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        init();
        initListeners();
        Bundle extras = getIntent().getExtras();
        socialMediaID = extras.getInt("SOCIAL_MEDIA_ID");
        switch( socialMediaID ){
            case R.id.btnYoutube:
                url = getString(R.string.youtubeURL);
                break;
            case  R.id.btnFacebook:
                url = getString(R.string.facebookURL);
                break;
            case  R.id.btnTwitter:
                url = getString(R.string.twitterURL);
                break;
            default:
                url = getString(R.string.website);
                break;
        }

        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webView.setBackgroundColor(Color.parseColor("#919191"));
        webView.loadUrl(url);
        webView.setWebViewClient(new MyWebViewClient());
    }

    private void init() {
        webView =(WebView) findViewById(R.id.webView);
        btnGoBack = (Button) findViewById(R.id.btnGoBack);
        webProgressBar = (ProgressBar) findViewById(R.id.webProgressBar);
        loadingTV =(TextView) findViewById(R.id.loadingTV);
    }

    private void initListeners(){
        btnGoBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGoBack:
                finish();
                break;
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            loadingTV.setVisibility(View.GONE);
            webProgressBar.setVisibility(View.GONE);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            webView.loadUrl("file:///android_asset/error.html");
        }
    }
}
