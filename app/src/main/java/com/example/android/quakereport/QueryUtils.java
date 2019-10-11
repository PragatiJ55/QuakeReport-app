package com.example.android.quakereport;

import android.text.TextUtils;
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
import java.util.ArrayList;
import java.util.List;


/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /** Sample JSON response for a USGS query */
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();


    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */

        // Create an empty ArrayList that we can start adding earthquakes to
        static ArrayList<Earthquake> earthquakes = new ArrayList<>();
        public static ArrayList fetchEarthQuakeData(String requestUrl)
        {
            URL url=createUrl(requestUrl);
            String JSONResponse=null;
            try{
                JSONResponse=makeHttpRequest(url);
            }catch (IOException e){
                Log.e(LOG_TAG,"Error closing input stream",e);
            }
            ArrayList earthquake=extractFeaturesfromJSON(JSONResponse);
            Log.v("EarthquakeActivity","fetchEarthQuakeData works");

            return earthquake;

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.

            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.}

        // Return the list of earthquakes
    }
    public static URL createUrl(String stringUrl)
        {
            URL url=null;
            try
            {
                url=new URL(stringUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return url;
        }
        private static String makeHttpRequest(URL url) throws IOException{
            String JSONresponse="";
            if(url==null)
            {
                return JSONresponse;
            }
            HttpURLConnection UrlConnection=null;
            InputStream inputStream=null;
            try{
                UrlConnection=(HttpURLConnection)url.openConnection();
                UrlConnection.setReadTimeout(10000 /* milliseconds */);
                UrlConnection.setConnectTimeout(15000 /* milliseconds */);
                UrlConnection.setRequestMethod("GET");
                UrlConnection.connect();
                if(UrlConnection.getResponseCode()==200)
                {
                    inputStream=UrlConnection.getInputStream();
                    JSONresponse=readFronStream(inputStream);
                }
                else
                {
                    Log.e(LOG_TAG,"Error response code"+UrlConnection.getResponseCode());
                }
            }catch (IOException e){
                Log.e(LOG_TAG,"Error retrieving Earthquake JSON response",e);
            }
            finally {
                if(UrlConnection!=null)
                {
                    UrlConnection.disconnect();
                }
                if(inputStream!=null)
                {
                    inputStream.close();
                }
            }
            return JSONresponse;

        }
        private static String readFronStream(InputStream inputStream)throws IOException{
            StringBuilder output=new StringBuilder();
            if(inputStream!=null)
            {
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                String line =bufferedReader.readLine();
                while(line!=null)
                {
                    output.append(line);
                    line=bufferedReader.readLine();
                }
            }
            return output.toString();
        }
        private static ArrayList<Earthquake> extractFeaturesfromJSON(String earthquakeJson)
        {
            if(TextUtils.isEmpty(earthquakeJson))
            {
                return null;
            }
            try{
                JSONObject baseJSONresponse=new JSONObject(earthquakeJson);
                JSONArray featureArray=baseJSONresponse.getJSONArray("features");
                if(featureArray.length()>0)
                {
                    for(int i=0;i<featureArray.length();i++) {
                        JSONObject firstfeature = featureArray.getJSONObject(i);
                        JSONObject properties = firstfeature.getJSONObject("properties");
                        double magnitude = properties.getDouble("mag");
                        String place = properties.getString("place");
                        long time = properties.getLong("time");
                        String url = properties.getString("url");
                        Earthquake earthquake=new Earthquake(magnitude,place,time,url);
                        earthquakes.add(earthquake);
                    }

                }

            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
            }
            return earthquakes;
        }

}
