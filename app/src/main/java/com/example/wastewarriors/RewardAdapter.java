package com.example.wastewarriors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RewardAdapter  extends RecyclerView.Adapter<RewardAdapter.RewardViewHolder> {
    private ArrayList<Reward> rewardList;

    public RewardAdapter(ArrayList<Reward> rewardList) {
        this.rewardList = rewardList;
    }

    @NonNull
    @Override
    public RewardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reward, parent, false);
        return new RewardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardViewHolder holder, int position) {
        Reward reward = rewardList.get(position);
        holder.rewardName.setText(reward.getName());
        String temp = String.valueOf(reward.getProgress());
        holder.rewardProgress.setText("Unlock Progress: "+ temp + "%");
    }

    @Override
    public int getItemCount() {
        return rewardList.size();
    }

    public class RewardViewHolder extends RecyclerView.ViewHolder {
        TextView rewardName;
        TextView rewardProgress;

        public RewardViewHolder(@NonNull View itemView) {
            super(itemView);
            rewardName = itemView.findViewById(R.id.rewardNameTv);
            rewardProgress = itemView.findViewById(R.id.rewardProgressTv);
        }
    }
}
