package com.example.android.quakereport;

public class Earthquake {
    private double mmag;
    private String mlocation;
    private long mtimeInmilliseconds;
    private String murl;
    public Earthquake(double mag, String location, long timeInmilliseconds,String url)
    {
        mmag=mag;
        mtimeInmilliseconds=timeInmilliseconds;
        mlocation=location;
        murl=url;

    }
    public double getMag()
    {
        return mmag;
    }
    public  long getDate(){
        return mtimeInmilliseconds;
    }
    public  String getLocation(){
        return mlocation;
    }
    public  String geturl(){
        return murl;
    }




}
