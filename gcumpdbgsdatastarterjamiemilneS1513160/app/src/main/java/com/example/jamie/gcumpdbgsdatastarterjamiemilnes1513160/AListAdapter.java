// Name                 Jamie Milne
// Student ID           S1513160
// Programme of Study   Computer Games Software Development
package com.example.jamie.gcumpdbgsdatastarterjamiemilnes1513160;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//class to pass data to the list view
public class AListAdapter extends ArrayAdapter {
    //to reference the main script
    private final Activity Mainactivity;
    //to store the list of earthquakes
    private final String[] eqArray;
    public String[]titles;
    public AListAdapter(Activity Mainactivity, String[] eqArray)
    {
        super(Mainactivity,R.layout.listtemplate);
        this.Mainactivity = Mainactivity;
        this.eqArray = eqArray;
        titles = eqArray;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return eqArray.length;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=Mainactivity.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listtemplate, null,true);
        //gets references to objects in the listtemplate.xml file
        TextView eqTextField = (TextView) rowView.findViewById(R.id.description);
        //sets the values of the text object to values from the array
        eqTextField.setText(eqArray[position]);
        return rowView;
    };
}
