/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Earthquake>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    // ArrayList<Earthquake> earthquakes;
    ListView earthquakeListView;
    EarthquakeAdapter adapter;
    TextView mNoDataTextView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);


        earthquakeListView = (ListView) findViewById(R.id.Earthquake_list);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        mNoDataTextView = (TextView) findViewById(R.id.textView1);

        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(1, null, this);

            // earthQuakeDataRetrieval task = new earthQuakeDataRetrieval();
            //   task.execute();
        }
        else {
            progressBar.setVisibility(View.INVISIBLE);
            earthquakeListView.setEmptyView( earthquakeListView);
            mNoDataTextView.setText("No Internet Connection");
        }
    }

    public void updateList (final ArrayList<Earthquake> earthquakes){

       // earthquakeListView = (ListView) findViewById(R.id.Earthquake_list);
        adapter = new EarthquakeAdapter(EarthquakeActivity.this,earthquakes);
        earthquakeListView.setAdapter(adapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Uri webpage = Uri.parse(earthquakes.get(position).getUrl());

                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                else {
                    Toast.makeText(EarthquakeActivity.this, "Link not available", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void noDatainList (){

       // earthquakeListView = (ListView) findViewById(R.id.Earthquake_list);
        earthquakeListView.setEmptyView( earthquakeListView);
       // mNoDataTextView = (TextView) findViewById(R.id.textView1);
        mNoDataTextView.setText("No Earthquakes Found");
    }

    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int i, Bundle bundle) {

        progressBar.setVisibility(View.VISIBLE);
        return new EarthquakeLoader(this);

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> data) {
        progressBar.setVisibility(View.INVISIBLE);
        if (data.isEmpty()){
             noDatainList();
        }
        else {
            updateList(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Earthquake>> loader) {

        if(adapter!=null){
            adapter.clear();
        }
    }




    private class earthQuakeDataRetrieval extends AsyncTask<Void,Void,ArrayList<Earthquake>>{

        @Override
        protected ArrayList<Earthquake> doInBackground(Void... params) {
            ArrayList<Earthquake>  earthquakes = QueryUtils.extractEarthquakes();
            return earthquakes;
        }

        @Override
        protected void onPostExecute(ArrayList<Earthquake> earthquakes) {
            updateList(earthquakes);
        }
    }




}
