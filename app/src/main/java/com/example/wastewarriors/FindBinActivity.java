package com.example.wastewarriors;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FindBinActivity extends AppCompatActivity {
    Button findBinButton, scanCodeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_bin);

        setViewIds();

        findBinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWasteTypeDialog();
            }
        });
    }

    private void setViewIds() {
        findBinButton = findViewById(R.id.btnFindBin);
        scanCodeButton = findViewById(R.id.btnScanCode);
    }

    private void showWasteTypeDialog() {
        final String[] wasteTypes = {"General Waste", "Recycling", "Glass", "Plastic", "I'm Not Sure"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Waste Type")
                .setItems(wasteTypes, (dialog, which) -> {
                    String selectedWasteType = wasteTypes[which];
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}