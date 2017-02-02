package com.example.android.quakereport;

import android.util.Log;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dhyan on 2017-01-13.
 */

public class Earthquake {


    double mag;
    String place,url;
    long time;

    Earthquake(double mag, String place, long time, String url){
         this.mag = mag;
        this.place=place;
        this.time=time;
        this.url=url;
    }

     String getDate() {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM ,dd, yyyy");
        Date dateObject = new Date(time);
        String date = dateFormatter.format(dateObject);

        return date;
    }

     String getTime() {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("H:mm:a");
        Date dateObject = new Date(time);
        String date = dateFormatter.format(dateObject);
        return date;
    }

     String getMag() {
        DecimalFormat formatter = new DecimalFormat("0.0");
        String output = formatter.format(mag);
        return output;
    }

     double getMagDouble() {
        return mag;
    }
    String getUrl(){return url;}

     String getPrimaryLocation(){
        String pLoc = place.substring(place.indexOf("of")+3,place.length());
        return pLoc;
    }

     String getSecondaryLocation(){
        String Loc = place.substring(0,place.indexOf("of")+2);
        return Loc;
    }
}
