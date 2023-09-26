package com.example.wastewarriors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
public class RouteAdapter extends
        RecyclerView.Adapter<RouteAdapter.RouteViewHolder> {
    private ArrayList<Route> routeList;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public RouteAdapter(ArrayList<Route> routeList) {
        this.routeList = routeList;
    }

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup
                                                      parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_route, parent, false);
        return new RouteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteViewHolder holder,
                                 int position) {
        Route route = routeList.get(position);
        holder.routeNumberValue.setText(route.getRouteNo());
        holder.binCountNumber.setText(route.getBinCount());
        holder.binZoneLocation.setText(route.getBinZone());

    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }

    public class RouteViewHolder extends RecyclerView.ViewHolder {

        TextView routeNumberValue;
        TextView binCountNumber;
        TextView binZoneLocation;
        Button getDirectionsButton;

        public RouteViewHolder(@NonNull View itemView) {
            super(itemView);

            routeNumberValue = itemView.findViewById(R.id.routeNumberValue);
            binCountNumber = itemView.findViewById(R.id.binCountNumber);
            binZoneLocation = itemView.findViewById(R.id.binZoneLocation);
            getDirectionsButton = itemView.findViewById(R.id.getDirectionsButton);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            });
        }
    }
}