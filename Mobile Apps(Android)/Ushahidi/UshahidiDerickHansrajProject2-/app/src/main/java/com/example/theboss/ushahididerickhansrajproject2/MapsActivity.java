package com.example.theboss.ushahididerickhansrajproject2;

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
 * Title: TicTacToe
 * Filename: MainActivity.java
 * Date Written: February 11, 2018
 * Due Date: February 12, 2018
 * Description: Defines the methods that will be used to create a basic tic tac toe
 * user interface. There will be 9 buttons in total. The nine buttons will represent
 * the tic tac toe board. There are two spinners located in the menu  along with a reset
 * button. The first spinner will allow player x to choose a color. The second spinner will
 * allow player o to choose a color. Once the first button is clicked an x will be placed there
 * and further clicks leads to an alternating input between 'X' and 'O'. If the reset button is
 * clicked the game will restart.
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
     * onCreate method -- This method will define and load the operations performed upon the launch
     * of the application.
     *
     * @param savedInstanceState The instance that is passed.
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
     * onCreate method -- This method will define and load the operations performed upon the launch
     * of the application.
     *
     * @param savedInstanceState The instance that is passed.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(googleMap.MAP_TYPE_HYBRID);
        ireland = new LatLng(53.349805, -6.26031);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ireland, 9));
    }
}
