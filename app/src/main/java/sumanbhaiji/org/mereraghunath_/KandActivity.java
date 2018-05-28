package sumanbhaiji.org.mereraghunath_;

import android.content.Context;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class KandActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView kandTV;
    private TextView pagenoTV;
    private String selectedKand;
    private Button buttonPrev;
    private Button buttonNext;
    private Button buttonGo;
    private LinearLayout navBar;
    private EditText pagenoInput;
    private String kand[];
    private int currentPageNo = 1;
    private int kandSize;
    public InputMethodManager imm;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kand);
        init();
        initListeners();

        Bundle extras = getIntent().getExtras();
        selectedKand = extras.getString("ID");

        switch(selectedKand){
            case "A":
                kand = getResources().getStringArray(R.array.A);
                break;
            case "B":
                kand = getResources().getStringArray(R.array.B);
                break;
            case "C":
                kand = getResources().getStringArray(R.array.C);
                break;
            case "D":
                kand = getResources().getStringArray(R.array.D);
                break;
            case "E":
                kand = getResources().getStringArray(R.array.E);
                break;
            case "F":
                kand = getResources().getStringArray(R.array.F);
                break;
            case "G":
                kand = getResources().getStringArray(R.array.G);
                break;
            case "HANUMAN_CHALISA":
                kand = getResources().getStringArray(R.array.HANUMAN_CHALISA);
                navBar.setVisibility(View.GONE);
                pagenoTV.setVisibility(View.GONE);
                break;
            case "RAM_RAKSHA_STOTRA":
                kand = getResources().getStringArray(R.array.RAM_RAKSHA_STOTRA);
                navBar.setVisibility(View.GONE);
                pagenoTV.setVisibility(View.GONE);
                break;
        }

        kandSize =  kand.length;
        kandTV.setText(Html.fromHtml(kand[currentPageNo]));
        pagenoTV.setText("Page No: " +(currentPageNo));
        hideKeybaord(pagenoInput);
        createBannerAd();

    }

    private void createBannerAd(){
        MobileAds.initialize(this,getResources().getString(R.string.admob_app_id));
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    private void init() {
        kandTV = (TextView) findViewById(R.id.kandTextView);
        pagenoTV = (TextView) findViewById(R.id.pagenoTV);
        buttonGo = (Button) findViewById(R.id.buttonGo);
        buttonNext = (Button) findViewById(R.id.buttonNext);
        buttonPrev = (Button) findViewById(R.id.buttonPrev);
        pagenoInput = (EditText) findViewById(R.id.pagenoInput);

       navBar = (LinearLayout) findViewById(R.id.navBar);
         imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }
    private void initListeners() {
        kandTV.setOnClickListener(this);
        buttonGo.setOnClickListener(this);
        buttonPrev.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
       // buttonNextBottom.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonNext:
                clickedNext();
                break;
            case R.id.buttonPrev:
                clickedPrev();
                break;
            case R.id.buttonGo:
                clickedGo();
                break;
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("current_page_no", currentPageNo);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //repopulate values
        currentPageNo = savedInstanceState.getInt("current_page_no");
        kandTV.setText(Html.fromHtml(kand[currentPageNo]));
        pagenoTV.setText("Page No: " +(currentPageNo));
    }

    private void clickedNext() {

       if( currentPageNo < kandSize-1 ) {
           currentPageNo += 1;
       } else {
           Toast.makeText(this, "Page Number Entered should be in page range 1 to "+ (kandSize-1), Toast.LENGTH_SHORT).show();
       }
        kandTV.setText(Html.fromHtml(kand[currentPageNo]));
        pagenoTV.setText("Page No: " +(currentPageNo));
        hideKeybaord(pagenoInput);

    }
    private void clickedPrev() {

        if( currentPageNo > 1 ) {
            currentPageNo -= 1;
        } else {
            Toast.makeText(this, "Page Number Entered should be in page range 1 to "+ (kandSize-1), Toast.LENGTH_SHORT).show();
        }
        kandTV.setText(Html.fromHtml(kand[currentPageNo]));
        pagenoTV.setText("Page No: " +(currentPageNo));
        hideKeybaord(pagenoInput);
    }
    private void clickedGo() {
        int goToPageNo = currentPageNo;
        if( !pagenoInput.getText().toString().isEmpty()){
            goToPageNo = Integer.parseInt(pagenoInput.getText().toString());
        }
        if (goToPageNo > 0 && goToPageNo < kandSize ) {
            currentPageNo = goToPageNo;
            kandTV.setText(Html.fromHtml(kand[currentPageNo]));
            pagenoTV.setText("Page No: " +(currentPageNo));
            hideKeybaord(pagenoInput);

        } else {

            Toast.makeText(this, "Page Number Entered should be in page range 1 to "+ (kandSize-1), Toast.LENGTH_SHORT).show();
        }
        pagenoInput.setText("");
    }

    private void hideKeybaord(View v) {
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}

