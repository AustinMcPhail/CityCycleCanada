package city.cycle.canada;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import src.city.cycle.canada.ForumPost;
import src.city.cycle.canada.ForumPostAdapter;
import src.city.cycle.canada.GoogleSignInService;

import static src.city.cycle.canada.Constants.RC_SIGN_IN;

public class Forum extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private GoogleSignInService googleSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.forum_activity);
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
        ArrayList<ForumPost> arrayOfPosts = new ArrayList<ForumPost>();
        // Create the adapter to convert the array to views
        final ForumPostAdapter adapter = new ForumPostAdapter(this, arrayOfPosts);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.forum_list_view);
        listView.setAdapter(adapter);

        //TODO: Request all posts from backend. Replace hardcoded post
        // Add item to adapter

                // START OF REQUEST
                String url = "http://www.deltanis:3000/forum";
                final RequestQueue rq = Volley.newRequestQueue(Forum.this);
                JsonArrayRequest jr = new JsonArrayRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>(){
                            @Override
                            public void onResponse(JSONArray response){
                                try{

                                    ForumPost newPost;
                                    for(int i=0; i< response.length(); i++){
                                        JSONObject post = response.getJSONObject(i);

                                        newPost = new ForumPost(post.getString("title"), post.getString("_id"), post.getString("userId"),1, post.getInt("score"), post.getString("userName"), post.getString("created"));
                                        adapter.add(newPost);
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
                // END OF REQUEST

        // Or even append an entire new collection
        // Fetching some data, data has now returned
        // If data was JSON, convert to ArrayList of User objects.
    }

    @Override
    protected void onStart(){
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        googleSignIn.refreshGoogleSignInUI(account);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.forum_activity);

        //Handle menu option actions
        if (id == R.id.list_stolen_bike) {
            Intent intent = new Intent(Forum.this, StolenBike.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.forum) {
        } else if (id == R.id.go_home) {
            finish();
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
        if (requestCode == 2){
            finish();
        }
    }

    public void goPost(View view) {
        int x = 0;
        Intent intent = new Intent(Forum.this, PostForm.class);
        startActivityForResult(intent,x);
    }
}