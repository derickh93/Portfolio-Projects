package com.example.n00827531.google_maps;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
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
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static String minStr;
    public static String maxStr;
    public static String descStr ;
    public static double latitude;
    public static double longitude;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new GetLocations().execute();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //calls internal class method
        //multithreading - multiple processes running at the same time in parallel
        //async task is a multithreaded abilty/implementation that runs another process in the background, easier to use as long as it is appropriate
        //async task should be used if the backgorund process can be done fairly simply
        //new GetLocations().execute();
    }

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
                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?id=5133268&APPID=cff54cf4393ce98abf4134eca01c4b27&units=imperial&cnt=5");
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
                    JSONObject jsonObj = new JSONObject(result);
                    JSONObject city = jsonObj.getJSONObject("city");
                    JSONObject coord = city.getJSONObject("coord");
                    JSONArray list = jsonObj.getJSONArray("list");

                        JSONObject dailyInfo = list.getJSONObject(0);
                        JSONObject tempInfo = dailyInfo.getJSONObject("temp");

                        JSONArray weather = dailyInfo.getJSONArray("weather");
                        JSONObject descr = weather.getJSONObject(0);

                        String day = tempInfo.getString("day");
                        String min = tempInfo.getString("min");
                        String max = tempInfo.getString("max");
                        String lat = coord.getString("lat");
                        String lon = coord.getString("lon");
                        String cityName = city.getString("name");
                        String des = descr.getString("description");

                        minStr = min;
                        maxStr = max;
                        descStr = des;
                        longitude = Double.parseDouble(lon);
                        latitude = Double.parseDouble(lat);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(googleMap.MAP_TYPE_HYBRID);
        LatLng gardenCity = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gardenCity, 15));
        mMap.addMarker(new MarkerOptions().position(gardenCity).title("High: " + maxStr + " :Low: " + minStr).snippet
                ("Description: " + descStr.toUpperCase()));
    }
}
