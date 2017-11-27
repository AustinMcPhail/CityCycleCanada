package city.cycle.canada;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import src.city.cycle.canada.ForumPost;
import src.city.cycle.canada.GoogleSignInService;
import src.city.cycle.canada.StolenBikeReport;
import src.city.cycle.canada.StolenBikeReportAdapter;

import static src.city.cycle.canada.Constants.RC_SIGN_IN;

public class StolenBike extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    private GoogleSignInService googleSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stolen_bike);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.stolen_bike_activity);
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

        // Construct the data source
        ArrayList<StolenBikeReport> arrayOfStolenBikeReports = new ArrayList<StolenBikeReport>();
        // Create the adapter to convert the array to views
        final StolenBikeReportAdapter adapter = new StolenBikeReportAdapter(this, arrayOfStolenBikeReports);
        // Attach the adapter to a ListView
        final ListView listView = (ListView) findViewById(R.id.stolen_bike_list_view);
        listView.setAdapter(adapter);

        //getBikeRequests(adapter);

        final SwipeRefreshLayout swiper = (SwipeRefreshLayout)findViewById(R.id.bike_swipe);
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swiper.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listView.setAdapter(null);
                        adapter.clear();
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);
                        //getBikeRequests(adapter);
                        swiper.setRefreshing(false);
                    }
                }, 500);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0)
                    swiper.setEnabled(true);
                else
                    swiper.setEnabled(false);
            }
        });

        //TODO: Request all posts from backend. Replace hardcoded post
        // Or even append an entire new collection
        // Fetching some data, data has now returned
        // If data was JSON, convert to ArrayList of User objects.

    }

    @Override
    protected void onStart(){
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        googleSignIn.refreshGoogleSignInUI(account);
        googleSignIn.setAccount(account);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        //Handle menu option actions
        if (id == R.id.list_stolen_bike) {
        } else if (id == R.id.forum) {
            Intent intent = new Intent(StolenBike.this, Forum.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.go_home) {
            finish();
        } else {
            //Shouldn't happen
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.stolen_bike_activity);
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

    public void createNewStolenBikeReport(View view){

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account == null){
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("Sign in required")
                    .setMessage("You must be signed in to report a stolen bike")
                    .setPositiveButton("Sign in", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            googleSignIn.signIn();
                        }
                    })
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        else{
            int x = 0;
            Intent intent = new Intent(StolenBike.this, StolenBikeForm.class);
            startActivityForResult(intent,x);
        }
    }

    public void getBikeRequests(final StolenBikeReportAdapter a) {
        // put request code here
        String url = "http://204.83.96.200:3000/stolenBikes";
        final RequestQueue rq = Volley.newRequestQueue(StolenBike.this);
        JsonArrayRequest jr = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response){
                        try{
                            int x = 0;
                            ForumPost newPost;
                            for(int i=0; i< response.length(); i++) {
                                JSONObject post = response.getJSONObject(i);

                                //request code here

                            }

                            rq.stop();
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.d("GET", "Something went wrong.");
                        error.printStackTrace();
                        rq.stop();
                    }
                });
        rq.add(jr);
        // end request code
    }
}
