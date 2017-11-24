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
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Date;

import src.city.cycle.canada.Comment;
import src.city.cycle.canada.CommentAdapter;
import src.city.cycle.canada.ForumPost;
import src.city.cycle.canada.GoogleSignInService;

import static src.city.cycle.canada.Constants.RC_SIGN_IN;

public class Post extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private GoogleSignInService googleSignIn;
    private ForumPost forumPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.post_activity);
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

        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();

        int postID = extrasBundle.getInt("postID", -1);
        //TODO: Write function to hit backend to request a post with ID postID
        forumPost = new ForumPost("Hardcoded post title!",postID,1,0,1,"Jerry",new Date());

        TextView viewPostTitle = findViewById(R.id.specific_post_title);
        viewPostTitle.setText(forumPost.title);

        TextView viewPostContent = findViewById(R.id.specific_post_content);
        viewPostContent.setText("Hardcoded post text! Also this post has postID=" + postID);


        //Setup comments
        // Construct the data source
        ArrayList<Comment> arrayOfComments = new ArrayList<Comment>();
        // Create the adapter to convert the array to views
        CommentAdapter adapter = new CommentAdapter(this, arrayOfComments);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.comment_list_view);
        listView.setAdapter(adapter);

        //TODO: Request all comments from backend. Replace hardcoded comment
        // Add item to adapter
        Comment comment = new Comment("This is a hardcoded comment!", 1,1,1000);
        adapter.add(comment);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        //Handle menu option actions
        if (id == R.id.list_stolen_bike) {
            Intent intent = new Intent(Post.this, StolenBike.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.forum) {
            finish();
        } else if (id == R.id.go_home) {
            finish();
        } else {
            //Shouldn't happen
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.post_activity);
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

    protected void onStop() {
        this.setResult(2);
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        this.setResult(2);
        super.onDestroy();
    }

}
