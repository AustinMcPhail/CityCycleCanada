package src.city.cycle.canada;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import city.cycle.canada.HomeScreen;
import city.cycle.canada.R;

import static src.city.cycle.canada.Constants.RC_SIGN_IN;

/**
 * Created by nicolas on 15/11/17.
 */

public class GoogleSignInService {
    private Context context;
    private Activity activity;

    private GoogleSignInAccount account;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;

    public GoogleSignInService(Context inContext, Activity inActivity){
        context = inContext;
        activity = inActivity;

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
    }

    public void setAccount(GoogleSignInAccount inAccount){
        account = inAccount;
    }
    public void setGso(GoogleSignInOptions inGso){
        gso = inGso;
    }
    public void setmGoogleSignInClient(GoogleSignInClient inMGoogleSignInClient){
        mGoogleSignInClient = inMGoogleSignInClient;
    }

    public GoogleSignInAccount getAccount() {
        return account;
    }
    public GoogleSignInOptions getGso(){
        return gso;
    }
    public GoogleSignInClient getMGoogleSignInClient(){
        return mGoogleSignInClient;
    }

    public void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);

    }
    public void signOut(){
        mGoogleSignInClient.signOut();
        hideSignOutButton();
        showSignInButton();
    }

    public void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);


            // Signed in successfully, remove sign in button add sign-out button
            hideSignInButton();
            showSignOutButton();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            System.out.println("signInResult:failed code=" + e.getStatusCode());
            // updateUI(null);
        }
    }

    public void showSignInButton(){
        NavigationView navigationView = (NavigationView) activity.findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);

        header.findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
    }
    public void hideSignInButton(){
        NavigationView navigationView = (NavigationView) activity.findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);

        header.findViewById(R.id.sign_in_button).setVisibility(View.GONE);
    }

    public void showSignOutButton(){
        NavigationView navigationView = (NavigationView) activity.findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);

        header.findViewById(R.id.logout_button).setVisibility(View.VISIBLE);
    }
    public void hideSignOutButton(){
        NavigationView navigationView = (NavigationView) activity.findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);

        header.findViewById(R.id.logout_button).setVisibility(View.GONE);
    }

    public void refreshGoogleSignInUI(GoogleSignInAccount inAccount){
        if (inAccount != null){
            hideSignInButton();
            showSignOutButton();
        }
        else{
            hideSignOutButton();
            showSignInButton();
        }
    }


}
