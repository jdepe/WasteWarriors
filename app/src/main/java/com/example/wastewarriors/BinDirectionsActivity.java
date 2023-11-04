package com.example.wastewarriors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import android.graphics.Color;
import android.widget.TextView;

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
import java.util.List;



public class BinDirectionsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private Button cancelDirectionsButton;
    private GoogleMap mMap;
    protected static final int LOCATION_REQUEST_CODE = 101;
    private LatLng currentLocation;
    private TextView travelTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bin_directions);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_directions);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        boolean startDirections = getIntent().getBooleanExtra("start_directions", false);
        if (startDirections) {
            startDirectionsToBin();
        }

        setViewIds();

        cancelDirectionsButton.setOnClickListener(view -> finish());
        travelTimeTextView = findViewById(R.id.travel_time_text_view); // Initialize this here, assuming it is in your layout
    }

    private void setViewIds() {
        cancelDirectionsButton = findViewById(R.id.btnCancelDirections);
    }

    // Triggered after the user responds to the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startDirectionsToBin();
            }
        }
    }

    private void startDirectionsToBin() {
        if (currentLocation == null) {
            Log.e("startDirectionsToBin", "Current location is not available.");
            return;
        }

        LatLng nearestBin = new LatLng(-27.854, 153.342); // Replace with the actual bin location from your app logic
        fetchDirections(currentLocation, nearestBin);
    }


    private void fetchDirections(LatLng origin, LatLng dest) {
        Thread thread = new Thread(() -> {
            try {
                String strUrl = getDirectionsUrl(origin, dest);
                URL url = new URL(strUrl);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                InputStream in = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                final String jsonResponse = sb.toString();

                // Run on UI thread
                runOnUiThread(() -> {
                    try {
                        JSONObject jsonObject = new JSONObject(jsonResponse);
                        drawRoute(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        // Origin of the route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of the route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Key
        String key = "key=" + "AIzaSyCSRLe1opEHJj5869JM9HvgelMud-UzBkk";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + key;
        // Output format
        String output = "json";
        // Building the url to the web service
        return "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
    }

    private void drawRoute(JSONObject directionsJSON) {
        // Parse the JSON data using DirectionsJSONParser
        DirectionsJSONParser parser = new DirectionsJSONParser();
        List<List<HashMap<String, String>>> routes = parser.parse(directionsJSON);

        ArrayList<LatLng> points;
        PolylineOptions lineOptions = null;

        // Traversing through all the routes
        for (int i = 0; i < routes.size(); i++) {
            points = new ArrayList<>();
            lineOptions = new PolylineOptions();

            // Fetching i-th route
            List<HashMap<String, String>> path = routes.get(i);

            // Fetching all the points in i-th route
            for (int j = 0; j < path.size(); j++) {
                HashMap<String, String> point = path.get(j);

                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                LatLng position = new LatLng(lat, lng);

                points.add(position);
            }

            // Adding all the points in the route to LineOptions
            lineOptions.addAll(points);
            lineOptions.width(10);
            lineOptions.color(Color.BLUE);
        }

        // Drawing polyline in the Google Map for the i-th route
        if (lineOptions != null && mMap != null) {
            mMap.addPolyline(lineOptions);
        } else {
            Log.d("onMapReady", "Without Polylines drawn");
        }
        try {
            JSONArray jRoutes = directionsJSON.getJSONArray("routes");
            for (int i = 0; i < jRoutes.length(); i++) {
                JSONObject route = jRoutes.getJSONObject(i);
                JSONArray legs = route.getJSONArray("legs");
                for (int j = 0; j < legs.length(); j++) {
                    JSONObject leg = legs.getJSONObject(j);
                    JSONObject durationObject = leg.getJSONObject("duration");
                    String durationText = durationObject.getString("text");
                    // Now you have the travel duration as a String
                    // You can use this string to display the duration to the user
                    showTravelTime(durationText); // Implement this method to update the UI
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showTravelTime(String travelTime) {
        // Update the UI to show travel time
        // You may want to use a TextView to display the travel time
        runOnUiThread(() -> {
            TextView travelTimeTextView = findViewById(R.id.travel_time_text_view); // Replace with your actual TextView ID
            travelTimeTextView.setText("Estimated travel time: " + travelTime);
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng nearestBin = new LatLng(-27.854, 153.342);
        mMap.addMarker(new MarkerOptions().position(nearestBin).title("Nearest Bin Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nearestBin, 18));
        // Current location setting should happen when you have a fix on the user's location
    }


    class DirectionsJSONParser {

        // Receives a JSONObject and returns a list of lists containing latitude and longitude
        public List<List<HashMap<String, String>>> parse(JSONObject jObject) {
            List<List<HashMap<String, String>>> routes = new ArrayList<>();
            JSONArray jRoutes;
            JSONArray jLegs;
            JSONArray jSteps;

            try {
                jRoutes = jObject.getJSONArray("routes");

                // Traversing all routes
                for (int i = 0; i < jRoutes.length(); i++) {
                    jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                    List<HashMap<String, String>> path = new ArrayList<>();

                    // Traversing all legs
                    for (int j = 0; j < jLegs.length(); j++) {
                        jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");

                        // Traversing all steps
                        for (int k = 0; k < jSteps.length(); k++) {
                            String polyline = "";
                            polyline = (String) ((JSONObject) ((JSONObject) jSteps.get(k)).get("polyline")).get("points");
                            List<LatLng> list = decodePoly(polyline);

                            // Traversing all points
                            for (int l = 0; l < list.size(); l++) {
                                HashMap<String, String> hm = new HashMap<>();
                                hm.put("lat", Double.toString(((LatLng) list.get(l)).latitude));
                                hm.put("lng", Double.toString(((LatLng) list.get(l)).longitude));
                                path.add(hm);
                            }
                        }
                        routes.add(path);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return routes;
        }

        private List<LatLng> decodePoly(String encoded) {

            List<LatLng> poly = new ArrayList<>();
            int index = 0, len = encoded.length();
            int lat = 0, lng = 0;

            while (index < len) {
                int b, shift = 0, result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;

                shift = 0;
                result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;

                LatLng p = new LatLng((((double) lat / 1E5)),
                        (((double) lng / 1E5)));
                poly.add(p);
            }

            return poly;
        }
    }
}


