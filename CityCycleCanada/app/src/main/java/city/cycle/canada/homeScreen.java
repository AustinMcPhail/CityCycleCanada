package city.cycle.canada;

import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;//
import android.support.design.widget.NavigationView;//
import android.support.design.widget.Snackbar;//
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;//
import android.support.v7.app.ActionBarDrawerToggle;//
import android.support.v7.app.AppCompatActivity;//
import android.support.v7.widget.Toolbar;//
import android.util.Log;
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


/**
 * Created by Nicolas on 11/4/2017.
 */

public class homeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    private static int RC_SIGN_IN = 1;

    //Google sign in
    private GoogleSignInAccount account;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;

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
        getMenuInflater().inflate(R.menu.testing_side_menu, menu);

        TextView navHeaderTitle = findViewById(R.id.nav_header_title);
        //TODO: We should move constant (especially strings) into their own resource files.
        //TODO: Android may offer some kind of localization thing if we ever want to support other languages
        navHeaderTitle.setText("City Cycle Canada");

        //Logout and login initialization
        findViewById(R.id.logout_button).setVisibility(View.INVISIBLE);

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.logout_button).setOnClickListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Google sign in code
    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.logout_button:
                signOut();
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
            handleSignInResult(task);
        }
    }

    private void signIn(){
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void signOut(){
        mGoogleSignInClient.signOut();
        findViewById(R.id.logout_button).setVisibility(View.GONE);
        findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);


            // Signed in successfully, remove sign in button add sign-out button
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.logout_button).setVisibility(View.VISIBLE);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            System.out.println("signInResult:failed code=" + e.getStatusCode());
           // updateUI(null);
        }
    }
    //End of Google sign in code

}
