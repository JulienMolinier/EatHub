package com.example.eathub.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.eathub.R;
import com.github.abdularis.civ.CircleImageView;

class CommentHolder extends RecyclerView.ViewHolder {
    private RatingBar rate;
    private CircleImageView img;
    private TextView restName;
    private TextView comment;

    CommentHolder(@NonNull View itemView) {
        super(itemView);
        rate = itemView.findViewById(R.id.ratingBar4);
        img = itemView.findViewById(R.id.ImgRestaurant);
        restName = itemView.findViewById(R.id.RestaurantName);
        comment = itemView.findViewById(R.id.comment);
    }

    RatingBar getRate() {
        return rate;
    }

    ImageView getImg() {
        return img;
    }

    TextView getRestName() {
        return restName;
    }

    TextView getComment() {
        return comment;
    }
}
