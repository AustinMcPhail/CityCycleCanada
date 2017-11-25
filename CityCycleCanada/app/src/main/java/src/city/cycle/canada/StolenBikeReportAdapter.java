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

import city.cycle.canada.R;

/**
 * Created by nicolas on 23/11/17.
 */

public class StolenBikeReportAdapter extends ArrayAdapter<StolenBikeReport> {

    private Context context;

    public StolenBikeReportAdapter(Context context, ArrayList<StolenBikeReport> stolenBikeReports) {
        super(context, 0, stolenBikeReports);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        StolenBikeReport stolenBikeReport = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.stolen_bike_listview_single_item, parent, false);
        }
        // Lookup view for data population
        TextView stolenBikeReportView = (TextView) convertView.findViewById(R.id.bike_desc);
        // Populate the data into the template view using the data object
        stolenBikeReportView.setText(stolenBikeReport.description);

        stolenBikeReportView.setTag(position);

        stolenBikeReportView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                // Access the row position here to get the correct data item
                StolenBikeReport stolenBikeReport = getItem(position);
                // Do what you want here...
                //TODO: Fix these when we have the activity to view a single report
                //Intent intent = new Intent(context, Post.class);
                //intent.putExtra("postID", post.postID);
                //context.startActivity(intent);
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }

}
