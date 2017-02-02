package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Dhyan on 2017-01-13.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake>{

    public EarthquakeAdapter(Context context, ArrayList<Earthquake> resource) {
        super(context, 0,resource);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Earthquake currentEarthquake = getItem(position);

        if (convertView ==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_view_spec,parent,false);
        }
        TextView eqMag = (TextView) convertView.findViewById(R.id.mag_TextView);


        TextView eqPrimaryLocation = (TextView) convertView.findViewById(R.id.primary_location_TextView);
        TextView eqSecondaryLocation = (TextView) convertView.findViewById(R.id.secondary_location_TextView);

        TextView eqDate = (TextView) convertView.findViewById(R.id.date_TextView);
        TextView eqTime = (TextView) convertView.findViewById(R.id.time_TextView);

        eqMag.setText(currentEarthquake.getMag());
        GradientDrawable magnitudeCircle = (GradientDrawable) eqMag.getBackground();
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagDouble());

        magnitudeCircle.setColor(magnitudeColor);


        eqPrimaryLocation.setText(currentEarthquake.getPrimaryLocation());
        eqSecondaryLocation.setText(currentEarthquake.getSecondaryLocation());
        eqDate.setText(currentEarthquake.getDate());
        eqTime.setText(currentEarthquake.getTime());


        return convertView;
    }


    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }










}
