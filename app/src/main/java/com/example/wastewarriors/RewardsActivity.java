package com.example.wastewarriors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RewardsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RewardAdapter rewardAdapter;

    BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_rewards);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(RewardsActivity.this, HomeActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_bin) {
                startActivity(new Intent(RewardsActivity.this, FindBinActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_rewards) {
                startActivity(new Intent(RewardsActivity.this, RewardsActivity.class));
                finish();
                return true;
            }
            return false;
        });

        recyclerView = findViewById(R.id.rewardRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Reward> rewardList = generateDummyData();

        rewardAdapter = new RewardAdapter(rewardList);
        recyclerView.setAdapter(rewardAdapter);
    }

    private ArrayList<Reward> generateDummyData() {
        ArrayList<Reward> rewardList = new ArrayList<>();

        rewardList.add(new Reward("Reward 1", 100));
        rewardList.add(new Reward("Reward 2", 75));
        rewardList.add(new Reward("Reward 3", 50));
        rewardList.add(new Reward("Reward 4", 25));
        rewardList.add(new Reward("Reward 5", 0));

        return rewardList;
    }
}