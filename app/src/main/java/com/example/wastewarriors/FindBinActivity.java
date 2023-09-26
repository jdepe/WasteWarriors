package com.example.wastewarriors;


import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FindBinActivity extends AppCompatActivity {
    Button findBinButton, scanCodeButton;
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_bin);

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
                startActivity(new Intent(FindBinActivity.this, FindBinActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_rewards) {
                startActivity(new Intent(FindBinActivity.this, RewardsActivity.class));
                finish();
                return true;
            }
            return false;
        });

        findBinButton.setOnClickListener(view -> {
                showWasteTypeDialog();
        });

        scanCodeButton.setOnClickListener(view -> {
            Intent intent = new Intent(FindBinActivity.this, qrScanActivity.class);
            startActivity(intent);
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
                dialog.dismiss();
                Intent intent = new Intent(FindBinActivity.this, BinDirectionsActivity.class);
                startActivity(intent);
        });

        glassButton.setOnClickListener(view -> {
                dialog.dismiss();
                Intent intent = new Intent(FindBinActivity.this, BinDirectionsActivity.class);
                startActivity(intent);
        });

        plasticButton.setOnClickListener(view -> {
                dialog.dismiss();
                Intent intent = new Intent(FindBinActivity.this, BinDirectionsActivity.class);
                startActivity(intent);
        });

        cancelButton.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog.show();
    }
}