package city.cycle.canada;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;//
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;//
import android.support.v7.app.ActionBarDrawerToggle;//
import android.support.v7.app.AppCompatActivity;//
import android.support.v7.widget.Toolbar;//
import android.view.Gravity;
import android.view.View;//
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.TimeUnit;

import src.city.cycle.canada.GoogleSignInService;

import static src.city.cycle.canada.Constants.RC_SIGN_IN;


/**
 * Created by Nicolas on 11/4/2017.
 */

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    //Google sign in
    private GoogleSignInAccount account;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInService googleSignIn;
    private static int TIMEOUT = 4000;

    private WebView googleMaps;
    @Override
    protected void onCreate(Bundle savedInstanceState){


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        header.findViewById(R.id.sign_in_button).setOnClickListener(this);
        header.findViewById(R.id.logout_button).setOnClickListener(this);


        googleSignIn = new GoogleSignInService(this,this);

        //Webview
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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    CoordinatorLayout c = (CoordinatorLayout) findViewById(R.id.splashLayout);
                    c.setVisibility(View.GONE);
            }
        },TIMEOUT);
    }

    public void goForum(View view) {
        Intent intent = new Intent(HomeScreen.this, Forum.class);
        startActivity(intent);
    }

    @Override
    protected void onStart(){
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        googleSignIn.setAccount(account);
        googleSignIn.refreshGoogleSignInUI(account);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen_settings_menu, menu);

        TextView navHeaderTitle = findViewById(R.id.nav_header_title);
        //TODO: We should move constant (especially strings) into their own resource files. Android may offer some kind of localization thing if we ever want to support other languages
        //TODO: Also I'm not sure this is the corect function for this code. This functional is actually originally for the "3 dots" menu, not the drawer.


        //Logout and login initialization

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        if (signInButton != null){
            signInButton.setSize(SignInButton.SIZE_STANDARD);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        //Handle menu option actions
        if (id == R.id.list_stolen_bike) {
            Intent intent = new Intent(HomeScreen.this, StolenBike.class);
            startActivity(intent);
        } else if (id == R.id.forum) {
            Intent intent = new Intent(HomeScreen.this, Forum.class);
            startActivity(intent);
        } else if (id == R.id.go_home) {
        } else {
            //Shouldn't happen
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Google sign in code
    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.sign_in_button:
                googleSignIn.signIn();
                break;
            case R.id.logout_button:
                googleSignIn.signOut();
                break;
            default:
                //This should never happen
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            googleSignIn.handleSignInResult(task);
        }
    }

}
