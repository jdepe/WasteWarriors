package com.example.wastewarriors;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.wastewarriors.Utils.SharedPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class HomeActivity extends AppCompatActivity {
    private ImageView mapImageView;
    private Button findBinButton, reportIssueButton, viewRewardsButton, routesButton, faultsButton;
    private CircularProgressIndicator rewardProgressIndicator;

    private BottomNavigationView bottomNav;

    private MaterialCardView managementCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setViewIds();

        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_bin) {
                startActivity(new Intent(HomeActivity.this, FindBinActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_rewards) {
                startActivity(new Intent(HomeActivity.this, RewardsActivity.class));
                finish();
                return true;
            }
            return false;
        });


        findBinButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, FindBinActivity.class);
            startActivity(intent);
        });

        viewRewardsButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, RewardsActivity.class);
            startActivity(intent);
        });

        reportIssueButton.setOnClickListener(view -> {
            showDialog();
        });

        if (SharedPrefManager.isAdmin()) {
            managementCV.setVisibility(View.VISIBLE);
            routesButton.setOnClickListener(view -> {
                Intent intent = new Intent(HomeActivity.this, RouteListActivity.class);
                startActivity(intent);
            });
            faultsButton.setOnClickListener(view -> {
                Intent intent = new Intent(HomeActivity.this, BinLocationActivity.class);
                startActivity(intent);
            });
        }
        else {
            managementCV.setVisibility(View.GONE);
        }
    }

    private void setViewIds() {
        mapImageView = findViewById(R.id.map_placeholder);
        findBinButton = findViewById(R.id.btnFindBin);
        reportIssueButton = findViewById(R.id.btnReportIssue);
        viewRewardsButton = findViewById(R.id.btnViewRewards);
        rewardProgressIndicator = findViewById(R.id.rewardProgress);
        bottomNav = findViewById(R.id.bottom_navigation);
        managementCV = findViewById(R.id.managementCard);
        routesButton = findViewById(R.id.btnRoutes);
        faultsButton = findViewById(R.id.btnReportedFaults);
    }

    private void showDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.reports, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);

        EditText editText1 = dialogView.findViewById(R.id.editText1);
        EditText editText2 = dialogView.findViewById(R.id.editText2);
        Button submitButton = dialogView.findViewById(R.id.submit_button);

        // Spinner
        Spinner spinner = dialogView.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_items, android.R.layout.simple_spinner_item); // assuming spinner_items is your string array
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        AlertDialog alertDialog = dialogBuilder.create();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text1 = editText1.getText().toString();
                String text2 = editText2.getText().toString();
                String spinnerValue = spinner.getSelectedItem().toString();
                Log.d("UserReports", "Text 1: " + text1 + ", Text 2: " + text2 + ", Spinner Value: " + spinnerValue);

                Toast.makeText(HomeActivity.this, "Submission Successful", Toast.LENGTH_LONG).show();

                alertDialog.dismiss();
            }

        });

        alertDialog.show();
    }
}