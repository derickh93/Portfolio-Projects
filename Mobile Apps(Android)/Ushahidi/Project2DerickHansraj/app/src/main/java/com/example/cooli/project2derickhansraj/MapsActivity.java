package com.example.cooli.project2derickhansraj;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Title: Project2DerickHansraj
 * Filename: MapsActivity.java
 * Date Written: April 18, 2018
 * Due Date: April 19, 2018
 * Description: This application will pull the last 5 incidents reported to fixyourstreets.ie.
 * Then it will parse the Json objects from the website to put 5 markers on a google map.
 * Each marker will describe the incident and give the reported date when clicked on.
 *
 * @author Derick Hansraj
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static String titleStr;
    public static String dateStr ;
    public static double latitudeStr;
    public static double longitudeStr;
    public static String descriptionStr;
    private GoogleMap mMap;
    MarkerOptions options;
    LatLng ireland;

    /**
     * onCreate method -- This method will define and load the operations performed upon the launch
     * of the application.
     *
     * @param savedInstanceState The instance that is passed.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new GetLocations().execute();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * getLocations method -- This method will define and load the operations performed with the use of AsyncTask.
     * Json data will be parsed and used to create markers for 5 different incidents.
     *
     */
    private class GetLocations extends AsyncTask<Void, Void, Void> {
        String result = "";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpURLConnection urlConnection;
            BufferedReader reader;

            try {
                URL url = new URL("http://www.fixyourstreet.ie/api?task=incidents");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String s;
                while ((s = reader.readLine()) != null) {
                    result += s;
                }
            } catch (Exception e) {
                Log.i("HttpAsyncTask", "EXCEPTION: " + e.getMessage());
            }

            Log.i("PARSING", "Returned string: " + result);
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(Void r) {
            super.onPostExecute(r);

            if (result.length() != 0) {
                Log.i("Parsing", "about to start" + result);
                try {
                    for(int i = 0; i < 5; i++) {
                        JSONObject jsonObj = new JSONObject(result);
                        JSONObject payload = jsonObj.getJSONObject("payload");

                        JSONArray incidents = payload.getJSONArray("incidents");

                        JSONObject arrIncident = incidents.getJSONObject(i);
                        JSONObject incidentInfo = arrIncident.getJSONObject("incident");

                        String incidentTitle = incidentInfo.getString("incidenttitle");
                        String incidentDate = incidentInfo.getString("incidentdate");
                        String locationLongitude = incidentInfo.getString("locationlongitude");
                        String locationLatitude = incidentInfo.getString("locationlatitude");
                        String incidentDescription = incidentInfo.getString("incidentdescription");


                        titleStr = incidentTitle;
                        dateStr = incidentDate;
                        longitudeStr = Double.parseDouble(locationLongitude);
                        latitudeStr = Double.parseDouble(locationLatitude);
                        descriptionStr = incidentDescription;
                        options = new MarkerOptions().title(titleStr).position
                                (new LatLng(latitudeStr,longitudeStr)).snippet(dateStr);
                        mMap.addMarker(options);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
        }
    }




    /**
     * onMapReady method -- This method will define and load the behaviours of the map.
     *
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(googleMap.MAP_TYPE_HYBRID);
        ireland = new LatLng(53.426848, -7.936613);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ireland, 7));
    }
}
