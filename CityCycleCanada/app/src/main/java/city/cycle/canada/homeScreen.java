package city.cycle.canada;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by Nicolas on 11/4/2017.
 */

public class homeScreen extends AppCompatActivity {
    private WebView googleMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        googleMaps = findViewById(R.id.googleMaps);

        //Need to review which of these we actually need.
        WebSettings webSettings = googleMaps.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(true);
        webSettings.setDefaultTextEncodingName("utf-8");

        googleMaps.loadUrl("file:///android_asset/simplemap.html");
        googleMaps.setWebChromeClient(new WebChromeClient());
    }
}
