package com.example.eathub.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eathub.R;
import com.example.eathub.activities.RestaurantActivity;
import com.example.eathub.models.VisitModel;

import java.util.List;

public class HistoryRVAdapter extends RecyclerView.Adapter<HistoryHolder> {
    private List<VisitModel> visits;
    private Context context;

    public HistoryRVAdapter(Context context, List<VisitModel> visits) {
        this.context = context;
        this.visits = visits;
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.visit_holder, viewGroup, false);


        return new HistoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder historyHolder, int i) {
        VisitModel visit = visits.get(i);
        historyHolder.getImgRestaurant().setImageResource(context.getResources().getIdentifier(
                visit.getRestaurant().getName().replaceAll(" ", "")
                        .replaceAll("-", "").toLowerCase(), "drawable",
                context.getPackageName()));
        historyHolder.getRestaurantName().setText(visit.getRestaurant().getName());
        historyHolder.getDate().setText(visit.getDate().toString());
        historyHolder.getPrice().setText(visit.getPrice() + " €");
        historyHolder.getCalories().setText(visit.getCalories() + " calories");
        historyHolder.getRatingBar().setRating((float) visit.getMark());

        historyHolder.itemView.setOnClickListener((View v) -> {
            Intent myIntent = new Intent(context, RestaurantActivity.class);
            myIntent.putExtra("currentRestaurant", visits.get(i).getRestaurant());
            myIntent.putExtra("currentProfile", visits.get(i).getProfileModel());
            context.startActivity(myIntent);
        });
    }

    @Override
    public int getItemCount() {
        return visits.size();
    }

}
