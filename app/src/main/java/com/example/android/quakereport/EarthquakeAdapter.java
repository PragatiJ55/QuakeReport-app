package com.example.android.quakereport;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.graphics.drawable.GradientDrawable;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

class EarthquakeAdapter  extends ArrayAdapter<Earthquake> {
    Context context;
    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes)
    {
        super(context,0,earthquakes);
        this.context=context;
    }
    private int getMagnitudeColor(double magnitude)
    {
        int magnitudeColorResourceId;
        int magFloor=(int)Math.floor(magnitude);
        switch(magFloor)
        {
            case 0:
            case 1:
                magnitudeColorResourceId=R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId=R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId=R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId=R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId=R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId=R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId=R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId=R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId=R.color.magnitude9;
                break;
            default:magnitudeColorResourceId=R.color.magnitude10plus;
        }
        return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_itemview, parent, false);
        }
        final Earthquake currentposition = getItem(position);
        TextView magnitudeView=(TextView)listItemView.findViewById(R.id.magnitude);
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();
        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentposition.getMag());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);
//magnitude
        TextView magnitude=(TextView)listItemView.findViewById(R.id.magnitude);
        double eqmag=currentposition.getMag();
        DecimalFormat formatter=new DecimalFormat("0.0");
        String stringdouble= formatter.format(eqmag);
        magnitude.setText(stringdouble);
        //location
        String place=currentposition.getLocation();
        TextView primarylocation=(TextView)listItemView.findViewById(R.id.primary_location);
        TextView offsetloction=(TextView)listItemView.findViewById(R.id.offset);
        if(place.contains("of"))
        {
            String[] parts=place.split("of");
            String offset=parts[0];
            String primary_location=parts[1];
            offsetloction.setText(offset+ "of" );
            primarylocation.setText(primary_location);

        }
        else
        {
            primarylocation.setText(place);
            offsetloction.setText(R.string.near_the);
        }



        //date
        Date dateObject=new Date(currentposition.getDate());
        SimpleDateFormat dateFormat=new SimpleDateFormat("MMM DD, YYYY");
        String formatteddate=dateFormat.format(dateObject);
        TextView date=(TextView)listItemView.findViewById(R.id.date);
        date.setText(formatteddate);
        //time
        SimpleDateFormat timeFormat=new SimpleDateFormat("hh:mm a");
        String formattedtime=timeFormat.format(dateObject);
        TextView time=(TextView)listItemView.findViewById(R.id.time);
        time.setText(formattedtime);
        return listItemView;
    }

}




