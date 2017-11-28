package city.cycle.canada;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.Task;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import src.city.cycle.canada.GoogleSignInService;

import static src.city.cycle.canada.Constants.MIME_BMP;
import static src.city.cycle.canada.Constants.RC_SIGN_IN;
import static src.city.cycle.canada.Constants.PICK_IMAGE_REQUEST;
import static src.city.cycle.canada.Constants.VALID_PICTURE_MIME_TYPES;

public class StolenBikeForm extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    private GoogleSignInService googleSignIn;
    private static final String TAG = StolenBikeForm.class.getSimpleName();

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


        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setHint("Location");
        autocompleteFragment.setBoundsBias(new LatLngBounds(
                new LatLng(50.39659, -104.75566),
                new LatLng(50.51911, -104.50381)));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
                LatLng latlng = place.getLatLng();
                String lat = Double.toString(latlng.latitude);
                String lon = Double.toString(latlng.longitude);

                TextInputEditText desc = findViewById(R.id.descriptionInput);
                desc.setTag(R.id.TAG_ONLINE_ID1, lat);
                desc.setTag(R.id.TAG_ONLINE_ID2, lon);

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

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

    public void submitStolenBikeReport(View view){

        final GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        TextInputEditText desc = findViewById(R.id.descriptionInput);
        TextInputEditText snum = findViewById(R.id.serialNumberInput);
        TextInputEditText pnum = findViewById(R.id.contactNumberInput);

        final String latitude = desc.getTag(R.id.TAG_ONLINE_ID1).toString();
        final String longitude = desc.getTag(R.id.TAG_ONLINE_ID2).toString();
        final String description = desc.getText().toString();
        final String serialNumber = snum.getText().toString();
        final String contact = pnum.getText().toString();

        if (validDescription(description) && validSerialNumber(serialNumber) && validPhoneNumber(contact)){

                    final String userId = account.getId();
                    final String userName = account.getDisplayName();

                    // START OF REQUEST
                    String url = "http://204.83.96.200:3000/stolenBikes/newReport";
                    final RequestQueue rq = Volley.newRequestQueue(StolenBikeForm.this);
                    StringRequest jr = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>(){
                                @Override
                                public void onResponse(String response){
                                    Log.d("Order", "Entered Request Task");
                                    Log.d("GET", response);
                                    Intent intent = new Intent(StolenBikeForm.this, StolenBike.class);
                                    startActivity(intent);
                                    finish();
                                    rq.stop();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error){
                                    Log.d("GET", "Something went wrong.");
                                    error.printStackTrace();
                                    rq.stop();
                                }
                            })
                    {
                        @Override
                        protected Map<String, String> getParams(){
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("serialNumber", serialNumber);
                            params.put("description", description);
                            params.put("userId", userId);
                            params.put("userName", userName);
                            params.put("latitude", latitude);
                            params.put("longitude", longitude);
                            params.put("contact", contact);

                            return params;
                        }
                    };
                    rq.add(jr);
                    // END OF REQUEST

                    int x = 0;
                    Intent intent = new Intent(StolenBikeForm.this, StolenBike.class);
                    startActivity(intent);
                    finish();

        }

    }



    public boolean validSerialNumber (String serialNumber){
        if (serialNumber.isEmpty()){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean validDescription (String description){
        if (description.isEmpty()){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean validPhoneNumber (String phoneNumber){
        if (phoneNumber.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }
}
