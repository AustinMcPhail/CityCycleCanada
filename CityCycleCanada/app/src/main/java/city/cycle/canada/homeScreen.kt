package city.cycle.canada

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebSettings
import android.webkit.WebChromeClient





class homeScreen : AppCompatActivity() {
    private lateinit var googleMaps: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        googleMaps = findViewById(R.id.googleMaps)

        //Need to review which of these we actually need.
        val webSettings = googleMaps.getSettings()
        webSettings.setJavaScriptEnabled(true)
        webSettings.setDomStorageEnabled(true)
        webSettings.setLoadWithOverviewMode(true)
        webSettings.setUseWideViewPort(true)
        webSettings.setBuiltInZoomControls(true)
        webSettings.setDisplayZoomControls(false)
        webSettings.setSupportZoom(true)
        webSettings.setDefaultTextEncodingName("utf-8")

        googleMaps.loadUrl("file:///android_asset/simplemap.html");
        googleMaps.setWebChromeClient(WebChromeClient());

    }
}
