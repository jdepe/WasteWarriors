package com.example.wastewarriors;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
public class BinAdapter extends
        RecyclerView.Adapter<BinAdapter.BinViewHolder> {
    private ArrayList<Bin> BinLocation;

    public BinAdapter(ArrayList<Bin> BinLocation) {
        this.BinLocation = BinLocation;
    }

    @NonNull
    @Override
    public BinViewHolder onCreateViewHolder(@NonNull ViewGroup
                                                    parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bin, parent, false);
        return new BinViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BinViewHolder holder,
                                 int position) {
        Bin bin = BinLocation.get(position);
        holder.binIdValue.setText(bin.getBinId());
        holder.reportedIssue.setText(bin.getIssue());
        holder.binLocation.setText(bin.getLocation());
        holder.directionsButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), BinDirectionsActivity.class);
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return BinLocation.size();
    }

    public class BinViewHolder extends RecyclerView.ViewHolder {
        ImageView binImage;
        TextView binIdValue;
        TextView reportedIssue;
        TextView binLocation;
        Button directionsButton;

        public BinViewHolder(@NonNull View itemView) {
            super(itemView);
            binImage = itemView.findViewById(R.id.binImage);
            binIdValue = itemView.findViewById(R.id.binIdValue);
            reportedIssue = itemView.findViewById(R.id.reportedIssue);
            binLocation = itemView.findViewById(R.id.binLocation);
            directionsButton = itemView.findViewById(R.id.cardRouteButton);
        }
    }
}