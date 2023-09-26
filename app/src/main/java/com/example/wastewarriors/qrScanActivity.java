package com.example.wastewarriors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class qrScanActivity extends AppCompatActivity {
    Button addCodeManuallyButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scan);

        setViewIds();

        addCodeManuallyButton.setOnClickListener(view -> {
            Intent intent = new Intent(qrScanActivity.this, RewardProgressActivity.class);
            startActivity(intent);
        });
    }

    private void setViewIds() {
        addCodeManuallyButton = findViewById(R.id.btnAddCodeManually);
    }
}