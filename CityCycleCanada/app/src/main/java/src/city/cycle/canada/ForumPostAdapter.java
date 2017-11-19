package src.city.cycle.canada;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

import city.cycle.canada.Forum;
import city.cycle.canada.Post;
import city.cycle.canada.R;
import city.cycle.canada.StolenBike;

/**
 * Created by nicolas on 18/11/17.
 */

public class ForumPostAdapter extends ArrayAdapter<ForumPost> {

    private Context context;

    public ForumPostAdapter(Context context, ArrayList<ForumPost> posts) {
        super(context, 0, posts);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ForumPost post = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.forum_listview_single_item, parent, false);
        }
        // Lookup view for data population
        Button postView = (Button) convertView.findViewById(R.id.forum_post);
        // Populate the data into the template view using the data object
        postView.setText(post.title);

        postView.setTag(position);

        postView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                // Access the row position here to get the correct data item
                ForumPost post = getItem(position);
                // Do what you want here...
                System.out.println("OnClick has Fired");
                Intent intent = new Intent(context, Post.class);
                context.startActivity(intent);
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }
}
