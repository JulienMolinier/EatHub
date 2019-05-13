package com.example.eathub.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.eathub.R;
import com.example.eathub.activities.RestaurantActivity;
import com.example.eathub.models.ProfileModel;
import com.example.eathub.models.VisitModel;
import com.example.eathub.models.databases.DatabaseHandler;

import java.util.List;

public class CommentRVAdapter extends RecyclerView.Adapter<CommentHolder> {

    private List<VisitModel> comments;
    private Context context;

    public CommentRVAdapter(Context context, List<VisitModel> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.comment_list_item, viewGroup, false);

        return new CommentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder commentHolder, int i) {
        VisitModel visit = comments.get(i);
        commentHolder.getImg().setImageResource(context.getResources().getIdentifier(
                visit.getProfileModel().getName().replaceAll(" ", "")
                        .replaceAll("-", "").toLowerCase(), "drawable",
                context.getPackageName()));
        commentHolder.getComment().setText(visit.getCommentary());
        commentHolder.getRate().setRating((float) visit.getMark());
        commentHolder.getOpenPhoto().setVisibility(View.INVISIBLE);
        if(visit.getImage()!=null) {
            commentHolder.getOpenPhoto().setVisibility(View.VISIBLE);
            Dialog popup = new Dialog(context);
            commentHolder.getOpenPhoto().setOnClickListener((View v) -> {
                popup.setContentView(R.layout.image_popup);
                Button buttonClose = popup.findViewById(R.id.buttonClose);
                ImageView phototaken = popup.findViewById(R.id.phototaken);
                phototaken.setImageBitmap(BitmapFactory.decodeFile(visit.getImage()));
                buttonClose.setOnClickListener((View v1) -> popup.dismiss());
                popup.show();
            });
        }
        commentHolder.itemView.setOnClickListener((View v) -> {
            Intent myIntent = new Intent(context, RestaurantActivity.class);
            myIntent.putExtra("currentRestaurant", comments.get(i).getRestaurant());
            myIntent.putExtra("currentProfile", comments.get(i).getProfileModel());
            context.startActivity(myIntent);
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

}