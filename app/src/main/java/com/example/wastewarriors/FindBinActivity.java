package com.example.wastewarriors;

import static com.example.wastewarriors.BinDirectionsActivity.LOCATION_REQUEST_CODE;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import android.location.Location;

public class FindBinActivity extends AppCompatActivity implements OnMapReadyCallback {
    Button findBinButton, scanCodeButton;
    BottomNavigationView bottomNav;
    private GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_bin);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        setViewIds();

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_bin);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(FindBinActivity.this, HomeActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_bin) {
                return true;
            } else if (itemId == R.id.nav_rewards) {
                startActivity(new Intent(FindBinActivity.this, RewardsActivity.class));
                finish();
                return true;
            }
            return false;
        });

        findBinButton.setOnClickListener(view -> showWasteTypeDialog());
        scanCodeButton.setOnClickListener(view -> {
            Intent intent = new Intent(FindBinActivity.this, qrScanActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Checking for location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            enableUserLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableUserLocation();
            } else {
                // Handle the case where the user denies the location permission
            }
        }
    }

    private void enableUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Task<Location> lastLocation = fusedLocationProviderClient.getLastLocation();
        lastLocation.addOnSuccessListener(this, location -> {
            if (location != null) {
                // Update the currentLocation variable
                currentLocation = location;

                // Set the map's camera position to the current location of the device.
                LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
                mMap.addMarker(new MarkerOptions().position(currentLatLng).title("Current Location"));
            }
        });
    }

    private void setViewIds() {
        findBinButton = findViewById(R.id.btnFindBin);
        scanCodeButton = findViewById(R.id.btnScanCode);
    }

    private void showWasteTypeDialog() {
        // Create dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_waste_type);
        dialog.setTitle("Select Waste Type");

        // Create buttons and set view ids
        Button plasticButton = dialog.findViewById(R.id.button_plastic);
        Button cancelButton = dialog.findViewById(R.id.button_cancel);
        Button glassButton = dialog.findViewById(R.id.button_glass);
        Button generalWasteButton = dialog.findViewById(R.id.button_general_waste);

        // Set button click functionality
        generalWasteButton.setOnClickListener(view -> {
            if (ActivityCompat.checkSelfPermission(FindBinActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(FindBinActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            } else {
                if (currentLocation != null) {
                    dialog.dismiss();
                    Intent intent = new Intent(FindBinActivity.this, BinDirectionsActivity.class);
                    intent.putExtra("start_directions", true);
                    intent.putExtra("start_latitude", currentLocation.getLatitude());
                    intent.putExtra("start_longitude", currentLocation.getLongitude());
                    startActivity(intent);
                } else {
                    // Handle the case where currentLocation is null
                    // e.g., show a message to the user or retry obtaining the location
                }
            }
        });

        glassButton.setOnClickListener(view -> {
            if (ActivityCompat.checkSelfPermission(FindBinActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(FindBinActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            } else {
                if (currentLocation != null) {
                    dialog.dismiss();
                    Intent intent = new Intent(FindBinActivity.this, BinDirectionsActivity.class);
                    intent.putExtra("start_directions", true);
                    intent.putExtra("start_latitude", currentLocation.getLatitude());
                    intent.putExtra("start_longitude", currentLocation.getLongitude());
                    startActivity(intent);
                } else {
                    // Handle the case where currentLocation is null
                    // e.g., show a message to the user or retry obtaining the location
                }
            }
        });

        plasticButton.setOnClickListener(view -> {
            if (ActivityCompat.checkSelfPermission(FindBinActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(FindBinActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            } else {
                if (currentLocation != null) {
                    dialog.dismiss();
                    Intent intent = new Intent(FindBinActivity.this, BinDirectionsActivity.class);
                    intent.putExtra("start_directions", true);
                    intent.putExtra("start_latitude", currentLocation.getLatitude());
                    intent.putExtra("start_longitude", currentLocation.getLongitude());
                    startActivity(intent);
                } else {
                    // Handle the case where currentLocation is null
                    // e.g., show a message to the user or retry obtaining the location
                }
            }
        });

        cancelButton.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }
}
