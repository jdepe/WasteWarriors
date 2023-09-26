package com.example.wastewarriors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.progressindicator.CircularProgressIndicator;

public class RewardProgressActivity extends AppCompatActivity {
    CircularProgressIndicator rewardProgressIndicator;
    Button progressRewardButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_progress);

        setViewIds();

        progressRewardButton.setOnClickListener(view -> {
            Intent intent = new Intent(RewardProgressActivity.this, RewardsActivity.class);
            startActivity(intent);
        });
    }

    private void setViewIds() {
        rewardProgressIndicator = findViewById(R.id.rewardProgress);
        progressRewardButton = findViewById(R.id.btnRewards);
    }
}