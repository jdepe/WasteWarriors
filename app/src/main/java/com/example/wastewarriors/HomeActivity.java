package com.example.wastewarriors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.progressindicator.CircularProgressIndicator;

public class HomeActivity extends AppCompatActivity {
    ImageView mapImageView;
    Button findBinButton, reportIssueButton, viewRewardsButton;
    CircularProgressIndicator rewardProgressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setViewIds();

        findBinButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, FindBinActivity.class);
            startActivity(intent);
        });
    }

    private void setViewIds() {
        mapImageView = findViewById(R.id.map_placeholder);
        findBinButton = findViewById(R.id.btnFindBin);
        reportIssueButton = findViewById(R.id.btnReportIssue);
        viewRewardsButton = findViewById(R.id.btnViewRewards);
        rewardProgressIndicator = findViewById(R.id.rewardProgress);
    }
}