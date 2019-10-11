package com.example.android.quakereport;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {
    String urls;
    public EarthquakeLoader(Context context,String url) {
        super(context);
        urls=url;
    }

    @Override
    protected void onStartLoading() {
        Log.v("EarthquakeActivity","OnStartLoading works");

        forceLoad();
    }

    @Override
    public ArrayList<Earthquake> loadInBackground() {
        if(urls == null && "".equals(urls)) {
            return null;
        }
        Log.v("EarthquakeActivity","loadInBackground works");
        return QueryUtils.fetchEarthQuakeData(urls);
    }
}
