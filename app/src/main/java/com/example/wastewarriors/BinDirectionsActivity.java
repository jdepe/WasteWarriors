package com.example.wastewarriors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class BinDirectionsActivity extends AppCompatActivity {
    Button cancelDirectionsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bin_directions);

        setViewIds();

        cancelDirectionsButton.setOnClickListener(view -> {
            Intent intent = new Intent(BinDirectionsActivity.this, FindBinActivity.class);
            startActivity(intent);
        });
    }

    private void setViewIds() {
        cancelDirectionsButton = findViewById(R.id.btnCancelDirections);
    }
}