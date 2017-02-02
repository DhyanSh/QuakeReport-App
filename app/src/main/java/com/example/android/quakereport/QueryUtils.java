package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.example.android.quakereport.EarthquakeActivity.LOG_TAG;

public final class QueryUtils {

    private QueryUtils() {
    }

    public static ArrayList<Earthquake> extractEarthquakes( ) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        String JSON_RESPONSE = null;

        try {
            JSON_RESPONSE = makeHttpRequest("http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10");
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (JSON_RESPONSE != null){
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject eqData = new JSONObject(JSON_RESPONSE);
            JSONArray features = eqData.getJSONArray("features");

            for (int i =0;i< features.length();i++){
                JSONObject earthquake  = features.getJSONObject(i);
                JSONObject properties = earthquake.getJSONObject("properties");

                double mag =  properties.getDouble("mag");
                String place =  properties.getString("place");
                long time =  properties.getLong("time");
                String url = properties.getString("url");

                earthquakes.add( new Earthquake(mag,place,time,url));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        }

        // Return the list of earthquakes
        return earthquakes;
    }

    private static String makeHttpRequest(String stringUrl) throws IOException{
        String jsonResponse=null;
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL (MalformedURLException)", e);
            return null;
        }

        if (url==null){return jsonResponse;}

        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;

        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200){
                inputStream=urlConnection.getInputStream();
                jsonResponse= readFromStream(inputStream);
            }
            else {
                Log.e(QueryUtils.class.getSimpleName(),"Error in Connection. Response Code: "+ urlConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.e(QueryUtils.class.getSimpleName(),"Error in Connection. IOException Catched: "+ e);
        }
        finally {
            if (urlConnection!=null){urlConnection.disconnect();}
            if (inputStream!=null){inputStream.close();}
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
   StringBuilder output = new StringBuilder();
        if (inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,Charset.defaultCharset());
            BufferedReader inputBufferedReader = new BufferedReader(inputStreamReader);
            String line = inputBufferedReader.readLine();
            while (line!=null){
                output.append(line);
                line=inputBufferedReader.readLine();
            }
        }
        return output.toString();
    }
}