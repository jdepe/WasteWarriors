package com.example.wastewarriors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.util.ArrayList;
public class BinLocationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BinAdapter binAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bin_location);

        recyclerView = findViewById(R.id.binRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Bin> binList = generateDummyData();

        binAdapter = new BinAdapter(binList);
        recyclerView.setAdapter(binAdapter);
    }
    // Replace this method with your actual vehicle data
    private ArrayList<Bin> generateDummyData() {
        ArrayList<Bin> binList = new ArrayList<>();
        binList.add(new Bin("B123", "Jaden", "Josh"));
        binList.add(new Bin("B123", "Jaden", "Josh"));

        return binList;
    }
}