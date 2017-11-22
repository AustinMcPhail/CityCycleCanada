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

import city.cycle.canada.Post;
import city.cycle.canada.R;

/**
 * Created by rousseln on 2017-11-21.
 */

public class CommentAdapter  extends ArrayAdapter<Comment> {

    private Context context;

    public CommentAdapter(Context context, ArrayList<Comment> posts) {
        super(context, 0, posts);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Comment comment = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_listview_single_item, parent, false);
        }
        // Lookup view for data population
        TextView textView = (TextView) convertView.findViewById(R.id.comment);
        // Populate the data into the template view using the data object
        textView.setText(comment.commentContent);

        textView.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }

}
