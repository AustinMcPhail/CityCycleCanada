package src.city.cycle.canada;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.title_row_layout, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.specific_post_title);
        // Populate the data into the template view using the data object
        textView.setText(post.title);
        textView = (TextView) convertView.findViewById(R.id.specific_post_score);
        textView.setText(Integer.toString(post.postScore));
        textView = (TextView) convertView.findViewById(R.id.post_author);
        textView.setText(post.userName);
        textView = (TextView) convertView.findViewById(R.id.post_date);
        textView.setText(post.postDate.toString());

        return convertView;
    }
}
