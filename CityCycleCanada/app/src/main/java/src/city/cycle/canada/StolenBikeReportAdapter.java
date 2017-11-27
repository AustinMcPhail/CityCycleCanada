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
import city.cycle.canada.StolenBike;

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
        stolenBikeReportView.setText(stolenBikeReport.description);
        /*stolenBikeReportView = (TextView) convertView.findViewById(R.id.specific_post_score);
        stolenBikeReportView.setText(Integer.toString(post.postScore));
        stolenBikeReportView = (TextView) convertView.findViewById(R.id.post_author);
        stolenBikeReportView.setText(stolenBikeReport.userName);
        stolenBikeReportView = (TextView) convertView.findViewById(R.id.post_date);
        stolenBikeReportView.setText(post.postDate.toString());*/

        stolenBikeReportView.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }

}
