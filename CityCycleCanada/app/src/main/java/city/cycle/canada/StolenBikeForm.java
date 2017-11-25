package city.cycle.canada;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

import src.city.cycle.canada.GoogleSignInService;

import static src.city.cycle.canada.Constants.RC_SIGN_IN;

public class StolenBikeForm extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    private GoogleSignInService googleSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stolen_bike_form);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.stolen_bike_form_activity);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        header.findViewById(R.id.sign_in_button).setOnClickListener(this);
        header.findViewById(R.id.logout_button).setOnClickListener(this);


        googleSignIn = new GoogleSignInService(this,this );
    }

    @Override
    protected void onStart(){
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        googleSignIn.refreshGoogleSignInUI(account);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        //Handle menu option actions
        if (id == R.id.list_stolen_bike) {
            Intent intent = new Intent(StolenBikeForm.this, StolenBike.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (id == R.id.forum) {
            finish();
        } else if (id == R.id.go_home) {
            Intent intent = new Intent(StolenBikeForm.this, HomeScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            //Shouldn't happen
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.stolen_bike_form_activity);
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

    public void submitStolenBikeReport(View vew){
        int x = 0;
        Intent intent = new Intent(StolenBikeForm.this, StolenBike.class);
        startActivity(intent);
        finish();
    }
}
