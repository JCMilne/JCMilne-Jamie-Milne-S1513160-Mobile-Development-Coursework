// Name                 Jamie Milne
// Student ID           S1513160
// Programme of Study   Computer Games Software Development

package com.example.jamie.gcumpdbgsdatastarterjamiemilnes1513160;

import android.content.Context;
import android.content.res.Configuration;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Time;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.zip.Inflater;

import com.example.jamie.gcumpdbgsdatastarterjamiemilnes1513160.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


//earthquake class
class Earthquake
{
    //variables to hold information of the earthquake
    String title;
    String date;
    int day;
    int month;
    int year;
    String category;
    float lattitude;
    float longitude;
    float depth;
    float magnitude;
    //getters and setters for the information

    void settitle(String input)
    {
        title = input;
    }
    String gettitle()
    {
        return title;
    }
    void setdate(String input)
    {
        date = input;
        String split = input;
        String[]temp = split.split(" ");
        day = Integer.parseInt(temp[1]);
        year = Integer.parseInt(temp[3]);
        switch (temp[2])
        {
            case("Jan"):
                month = 1;
                break;
            case("Feb"):
                month = 2;
                break;
            case("Mar"):
                month = 3;
                break;
            case("Apr"):
                month = 4;
                break;
            case("May"):
                month = 5;
                break;
            case("Jun"):
                month = 6;
                break;
            case("Jul"):
                month = 7;
                break;
            case("Aug"):
                month = 8;
                break;
            case("Sep"):
                month = 9;
                break;
            case("Oct"):
                month = 10;
                break;
            case("Nov"):
                month = 11;
                break;
            case("Dec"):
                month = 12;
                break;
        }

    }
    String getdate()
    {
        return date;
    }

    void setcategory(String input)
    {
        category = input;
    }
    String getcategory()
    {
        return category;
    }

    void setlattitude(float input)
    {
        lattitude = input;

    }
    float getlattitude()
    {
        return lattitude;
    }

    void setlongitude(float input)
    {
        longitude = input;

    }
    float getlongitude()
    {
        return longitude;
    }

    void setdepth(float input)
    {
        depth = input;

    }
    float getdepth()
    {
        return depth;
    }

    void setmagnitude(float input)
    {
        magnitude = input;

    }
    float getmagnitude()
    {
        return magnitude;
    }
}


public class MainActivity extends AppCompatActivity implements OnClickListener
{
    LinkedList<Earthquake> earthquakeList;
    LinkedList<Earthquake> displayedQuakes;
    private MainActivity mainScript;
    private ListView listDisplay;
    private AListAdapter adapter;
    private String result;
    private String url1="";
    private String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    private Button northBTN;
    private Button eastBTN;
    private Button southBTN;
    private Button westBTN;
    private Button depthBTN;
    private Button magnitudeBTN;
    private Button allBTN;
    private EditText Day;
    private EditText Month;
    private EditText Year;
    private View Popup;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //checks the orientation
        MainActivity.this.runOnUiThread(new Runnable()
        {
            public void run() {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                {
                    setContentView(R.layout.activity_main_landscape);
                }
                else
                {
                    setContentView(R.layout.activity_main_portrait);
                }
            }
        });


        // Set up the raw links to the graphical components
        mainScript = this;
        displayedQuakes = new LinkedList<Earthquake>();
        northBTN= (Button)findViewById(R.id.north);
        northBTN.setOnClickListener(this);
        eastBTN= (Button)findViewById(R.id.east);
        eastBTN.setOnClickListener(this);
        southBTN= (Button)findViewById(R.id.south);
        southBTN.setOnClickListener(this);
        westBTN= (Button)findViewById(R.id.weat);
        westBTN.setOnClickListener(this);
        depthBTN= (Button)findViewById(R.id.depth);
        depthBTN.setOnClickListener(this);
        magnitudeBTN= (Button)findViewById(R.id.magnitude);
        magnitudeBTN.setOnClickListener(this);
        allBTN= (Button)findViewById(R.id.all);
        allBTN.setOnClickListener(this);
        Day= findViewById(R.id.DayInput);
        Month= findViewById(R.id.MonthInput);
        Year= findViewById(R.id.YearInput);
        LayoutInflater popupInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = popupInflater.inflate(R.layout.popup, null);
        startProgress();
        listDisplay = findViewById(R.id.dataDisplay);
        //creates a popupwindow when an item on the list is clicked to provide more information
        listDisplay.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                final PopupWindow popup = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT, true);
                popup.showAtLocation(view,Gravity.CENTER,0,0);
                //updates the popup with th information from the clicked earthquake from the list
                TextView Title = popupView.findViewById(R.id.Title);
                TextView Date = popupView.findViewById(R.id.Date);
                TextView Category = popupView.findViewById(R.id.Category);
                TextView Latitude = popupView.findViewById(R.id.Latitude);
                TextView Longitude = popupView.findViewById(R.id.Longitude);
                TextView Depth = popupView.findViewById(R.id.Depth);
                TextView Magnitude = popupView.findViewById(R.id.Magnitude);
                Title.setText(displayedQuakes.get(position).gettitle());
                Date.setText("Date: " + displayedQuakes.get(position).getdate());
                Category.setText("Category: " + displayedQuakes.get(position).getcategory());
                Latitude.setText("Latitude: " + displayedQuakes.get(position).getlattitude());
                Longitude.setText("Longitude " + displayedQuakes.get(position).getlongitude());
                Depth.setText("Depth(k/m): " + displayedQuakes.get(position).getdepth());
                Magnitude.setText("Magnitude: " + displayedQuakes.get(position).getmagnitude());
            }
        });
    }

    public void onClick(View aview)
    {
        //runs search moethods for the search buttons
        if(aview == northBTN)
        {
            LinkedList<Earthquake> eqlist = new LinkedList<Earthquake>();
            LinkedList<Earthquake> eqlist2 = new LinkedList<Earthquake>();
            //checks for an entered date
            if(Day.getText().toString().trim().isEmpty() == false && Month.getText().toString().trim().isEmpty() == false && Year.getText().toString().trim().isEmpty() == false)
            {
                eqlist2 = TimePeriod((Integer.parseInt(Day.getText().toString())),(Integer.parseInt(Month.getText().toString())),(Integer.parseInt(Year.getText().toString())));
                if(eqlist2.size()>0)
                {
                    eqlist.add(highestlat(eqlist2));
                }
            }
            else
            {
                eqlist.add(highestlat(earthquakeList));
            }
            UpdateUI(eqlist);;
        }
        else if(aview == eastBTN)
        {
            LinkedList<Earthquake> eqlist = new LinkedList<Earthquake>();
            LinkedList<Earthquake> eqlist2 = new LinkedList<Earthquake>();
            //checks for an entered date
            if(Day.getText().toString().trim().isEmpty() == false && Month.getText().toString().trim().isEmpty() == false && Year.getText().toString().trim().isEmpty() == false)
            {
                eqlist2 = TimePeriod((Integer.parseInt(Day.getText().toString())),(Integer.parseInt(Month.getText().toString())),(Integer.parseInt(Year.getText().toString())));
                if(eqlist2.size()>0)
                {
                    eqlist.add(highestlong(eqlist2));
                }
            }
            else
            {
                eqlist.add(highestlong(earthquakeList));
            }
            UpdateUI(eqlist);
        }
        else if(aview == southBTN)
        {
            LinkedList<Earthquake> eqlist = new LinkedList<Earthquake>();
            LinkedList<Earthquake> eqlist2 = new LinkedList<Earthquake>();
            //checks for an entered date
            if(Day.getText().toString().trim().isEmpty() == false && Month.getText().toString().trim().isEmpty() == false && Year.getText().toString().trim().isEmpty() == false)
            {
                eqlist2 = TimePeriod((Integer.parseInt(Day.getText().toString())),(Integer.parseInt(Month.getText().toString())),(Integer.parseInt(Year.getText().toString())));
                if(eqlist2.size()>0)
                {
                    eqlist.add(lowestlat(eqlist2));
                }
            }
            else
            {
                eqlist.add(lowestlat(earthquakeList));
            }
            UpdateUI(eqlist);
        }
        else if(aview == westBTN)
        {
            LinkedList<Earthquake> eqlist = new LinkedList<Earthquake>();
            LinkedList<Earthquake> eqlist2 = new LinkedList<Earthquake>();
            //checks for an entered date
            if(Day.getText().toString().trim().isEmpty() == false && Month.getText().toString().trim().isEmpty() == false && Year.getText().toString().trim().isEmpty() == false)
            {
                eqlist2 = TimePeriod((Integer.parseInt(Day.getText().toString())),(Integer.parseInt(Month.getText().toString())),(Integer.parseInt(Year.getText().toString())));
                if(eqlist2.size()>0)
                {
                    eqlist.add(lowestlong(eqlist2));
                }
            }
            else
            {
                eqlist.add(lowestlong(earthquakeList));
            }
            UpdateUI(eqlist);
        }
        else if(aview == depthBTN)
        {
            LinkedList<Earthquake> eqlist = new LinkedList<Earthquake>();
            LinkedList<Earthquake> eqlist2 = new LinkedList<Earthquake>();
            //checks for an entered date
            if(Day.getText().toString().trim().isEmpty() == false && Month.getText().toString().trim().isEmpty() == false && Year.getText().toString().trim().isEmpty() == false)
            {
                eqlist2 = TimePeriod((Integer.parseInt(Day.getText().toString())),(Integer.parseInt(Month.getText().toString())),(Integer.parseInt(Year.getText().toString())));
                if(eqlist2.size()>0)
                {
                    eqlist.add(highestdepth(eqlist2));
                }
            }
            else
            {
                eqlist.add(highestdepth(earthquakeList));
            }
            UpdateUI(eqlist);
        }
        else if(aview == magnitudeBTN)
        {
            LinkedList<Earthquake> eqlist = new LinkedList<Earthquake>();
            LinkedList<Earthquake> eqlist2 = new LinkedList<Earthquake>();
            //checks for an entered date
            if(Day.getText().toString().trim().isEmpty() == false && Month.getText().toString().trim().isEmpty() == false && Year.getText().toString().trim().isEmpty() == false)
            {
                eqlist2 = TimePeriod((Integer.parseInt(Day.getText().toString())),(Integer.parseInt(Month.getText().toString())),(Integer.parseInt(Year.getText().toString())));
                if(eqlist2.size()>0)
                {
                    eqlist.add(highestmag(eqlist2));
                }
            }
            else
            {
                eqlist.add(highestmag(earthquakeList));
            }
            UpdateUI(eqlist);
        }
        else if(aview == allBTN)
        {
            LinkedList<Earthquake> eqlist = new LinkedList<Earthquake>();
            //checks for an entered date
            if(Day.getText().toString().trim().isEmpty() == false && Month.getText().toString().trim().isEmpty() == false && Year.getText().toString().trim().isEmpty() == false)
            {
                eqlist = TimePeriod((Integer.parseInt(Day.getText().toString())),(Integer.parseInt(Month.getText().toString())),(Integer.parseInt(Year.getText().toString())));
            }
            else
            {
                eqlist = earthquakeList;
            }
            if(eqlist.size()>0)
            {
                UpdateUI(eqlist);
            }
        }

        //startProgress();
    }
    
    public void startProgress()
    {
        // Run network access on a separate thread;
        new Thread(new UpdateEarthquakedata(urlSource)).start();
    } //

    private class UpdateEarthquakedata implements Runnable
    {
        private String url;
        public UpdateEarthquakedata(String aurl)
        {
            url = aurl;
        }
        @Override
        public void run()
        {
            result = "";
            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";
            try
            {
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                // Throw away the first 2 header lines before parsing
                while ((inputLine = in.readLine()) != null)
                {
                    result = result + inputLine;
                }
                in.close();
            }
            catch (IOException ae)
            {
            }

            //parses the retrieved string
            parseData((result));
            UpdateUI(earthquakeList);
             //starts the timer on a new thread so that the data gets updated again in 5 minutes
             new Thread(new UpdateTimer()).start();
        }


        //method to handle the parsing of data
        public void parseData(String data)
        {
            System.out.print("At least the fucking console is working");
            //generates an earthquake class
            Earthquake eq = new Earthquake();
            //clears the list of earthquakes
            earthquakeList = new LinkedList<Earthquake>();
            //gets the inputted string
            String rawData = data;
            //creates the xlm pull parser
            XmlPullParserFactory factory;
            try
            {
                factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser parser = factory.newPullParser();
                parser.setInput( new StringReader( rawData ) );
                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT)
                {
                    // Found a start tag
                    if(eventType == XmlPullParser.START_TAG)
                    {
                        // Check which Tag we have
                        if (parser.getName().equalsIgnoreCase("item"))
                        {
                            // generates a fresh earthquake class when the parser gets to a new item
                             eq = new Earthquake();
                        }
                        else
                        if (parser.getName().equalsIgnoreCase("title"))
                        {
                            //sets the title to the retrieved title
                            eq.settitle(parser.nextText());
                        }
                        else
                        if (parser.getName().equalsIgnoreCase("pubDate"))
                        {
                            //sets the earthquakes date to the retrieved date
                            eq.setdate(parser.nextText());
                        }
                        else
                        if (parser.getName().equalsIgnoreCase("category"))
                        {
                            //retrieves the category from the parser and passes it to the earthquake
                            eq.setcategory(parser.nextText());
                        }
                        else
                        if (parser.getName().equalsIgnoreCase("geo:lat"))
                        {
                            //converts the retrieved string to a float and passes it to the earthquake as latitude
                            eq.setlattitude(Float.parseFloat(parser.nextText()));
                        }
                        else
                        if (parser.getName().equalsIgnoreCase("geo:long"))
                        {
                            //converts the retrieved string to a float and passes it to the earthquake as longitude
                            eq.setlongitude(Float.parseFloat(parser.nextText()));
                        }
                        else if(parser.getName().equalsIgnoreCase(("description")))
                        {
                            //splits the description string to retrieve the depth and magnitude of the earthquake
                            String split = parser.nextText();
                            String[] temp = split.split(":");
                            if(temp.length > 1)
                            {
                                String latlong = temp[5];
                                String[]temp2 = latlong.split(",");
                                eq.setlattitude(Float.parseFloat(temp2[0]));
                                latlong = temp2[1];
                                temp2 = latlong.split(";");
                                eq.setlongitude(Float.parseFloat(temp2[0]));
                                eq.setmagnitude(Float.parseFloat(temp[7]));
                                split = temp[6];
                                temp = split.split("k");
                                eq.setdepth(Float.parseFloat(temp[0]));

                            }

                        }

                    }
                    else
                    if(eventType == XmlPullParser.END_TAG)
                    {
                        if (parser.getName().equalsIgnoreCase("item"))
                        {
                            earthquakeList.add(eq);

                        }
                    }
                    // Get the next event
                    eventType = parser.next();
                } // loop ends when the parser reaches the end of the string
            }
            catch (XmlPullParserException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    //timer wait for 5 minutes then calls the update data function
    private class UpdateTimer implements Runnable
    {
        @Override
        public void run()
        {
            for(int i = 0; i < 300; i++)
            {
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            new Thread(new UpdateEarthquakedata(urlSource)).start();
        }
    }
    //updates the UI with the retrieved earthquake information
    public void UpdateUI(LinkedList<Earthquake> eqlist)
    {
        final LinkedList<Earthquake> list = eqlist;
        displayedQuakes = eqlist;
        MainActivity.this.runOnUiThread(new Runnable()
        {
            public void run() {
                Log.d("UI thread", "I am the UI thread");
                listDisplay = (ListView)findViewById(R.id.dataDisplay) ;
                String[]descriptions = new String[list.size()];
                for(int i = 0; i < list.size(); i++)
                {
                    descriptions[i] = list.get(i).gettitle();
                }
                adapter = new AListAdapter(mainScript, descriptions);
                listDisplay.setAdapter(adapter);
            }
        });
    }
    //returns the earthquake with the highest latitude
    Earthquake highestlat(LinkedList<Earthquake> quakes)
    {Earthquake highest = quakes.get(0);
        for(int i = 0; i < quakes.size(); i++)
        {
            if(highest.getlattitude() < quakes.get(i).getlattitude())
            {
                highest = quakes.get(i);
            }
        }
        return highest;
    }

    //returns the earthquake with the lowest latitude
    Earthquake lowestlat(LinkedList<Earthquake> quakes)
    {
        Earthquake highest = quakes.get(0);
        for(int i = 0; i < quakes.size(); i++)
        {
            if(highest.getlattitude() > quakes.get(i).getlattitude())
            {
                highest = quakes.get(i);
            }
        }
        return highest;
    }

    //returns the earthquake with the highest longitude
    Earthquake highestlong(LinkedList<Earthquake> quakes)
    {
        Earthquake highest = quakes.get(0);
        for(int i = 0; i < quakes.size(); i++)
        {
            if(highest.getlongitude() < quakes.get(i).getlongitude())
            {
                highest = quakes.get(i);
            }
        }
        return highest;
    }

    //returns the earthquake with the lowest longitude
    Earthquake lowestlong(LinkedList<Earthquake> quakes)
    {
        Earthquake highest = quakes.get(0);
        for(int i = 0; i < quakes.size(); i++)
        {
            if(quakes.get(i).getlongitude() < highest.getlongitude() )
            {
                highest = quakes.get(i);
            }
        }
        return highest;
    }

    //returns the earthquake with the highest magnitude
    Earthquake highestmag(LinkedList<Earthquake> quakes)
    {
        Earthquake highest = quakes.get(0);
        for(int i = 0; i < quakes.size(); i++)
        {
            if(highest.getmagnitude() < quakes.get(i).getmagnitude())
            {
                highest = quakes.get(i);
            }
        }
        return highest;
    }

    //returns the earthquake with the highest depth
    Earthquake highestdepth(LinkedList<Earthquake> quakes)
    {
        Earthquake highest = quakes.get(0);
        for(int i = 0; i < quakes.size(); i++)
        {
            if(highest.getdepth() < quakes.get(i).getdepth())
            {
                highest = quakes.get(i);
            }
        }
        return highest;
    }

    //returns a list of earthquakes on a specific date
    LinkedList<Earthquake> TimePeriod(int day, int month, int year)
    {
        LinkedList<Earthquake> eqList = new LinkedList<Earthquake>();
        //adds all earthquakes with matching date
        for (Earthquake e: earthquakeList)
        {
            if(e.day == day && e.month == month && e.year == year)
            {
                eqList.add(e);
            }
        }
        //returns the list
        return  eqList;
    }
}