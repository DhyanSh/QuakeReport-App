package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dhyan on 2017-01-17.
 */

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>>{

    public EarthquakeLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public ArrayList<Earthquake> loadInBackground() {
        ArrayList<Earthquake>  earthquakes = QueryUtils.extractEarthquakes();
        return earthquakes;

    }
}
