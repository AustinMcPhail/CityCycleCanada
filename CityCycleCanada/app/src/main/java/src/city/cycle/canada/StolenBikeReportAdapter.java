package src.city.cycle.canada;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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

        /*
        ImageView stolenBikeImage = (ImageView) convertView.findViewById(R.id.Bike_pic);
        Resources res = getResources();
        int rid = res.getIdentifier(generatedString,"drawable",getPackageName());
        stolenBikeImage.setImaageResource(rid);
        */

        TextView stolenBikeReportView = (TextView) convertView.findViewById(R.id.bike_desc);
        stolenBikeReportView.setText(stolenBikeReport.description);
        stolenBikeReportView = (TextView) convertView.findViewById(R.id.bike_user);
        stolenBikeReportView.setText(stolenBikeReport.userID);
        stolenBikeReportView = (TextView) convertView.findViewById(R.id.bike_serial);
        stolenBikeReportView.setText(stolenBikeReport.serialNumber);
        stolenBikeReportView = (TextView) convertView.findViewById(R.id.bike_contact);
        stolenBikeReportView.setText(stolenBikeReport.contact);
        stolenBikeReportView = (TextView) convertView.findViewById(R.id.bike_date);
        stolenBikeReportView.setText(stolenBikeReport.stolenDate);

        stolenBikeReportView.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }

}
