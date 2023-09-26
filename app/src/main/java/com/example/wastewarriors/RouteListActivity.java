package com.example.wastewarriors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;
public class RouteListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RouteAdapter routeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_list);

        recyclerView = findViewById(R.id.routeRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Route> RouteList = generateDummyData();

        routeAdapter = new RouteAdapter(RouteList);
        recyclerView.setAdapter(routeAdapter);

        routeAdapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(RouteListActivity.this, BinDirectionsActivity.class);
            startActivity(intent);
        });
    }
    private ArrayList<Route> generateDummyData() {
        ArrayList<Route> RouteList = new ArrayList<>();
        RouteList.add(new Route("B123", "Jaden", "Josh"));
        RouteList.add(new Route("B123", "Jaden", "Josh"));

        return RouteList;
    }
}