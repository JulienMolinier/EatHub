package com.example.eathub.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.eathub.R;
import com.github.abdularis.civ.CircleImageView;

class CommentHolder extends RecyclerView.ViewHolder {
    private RatingBar rate;
    private CircleImageView img;
    private TextView comment;
    private ImageView imageView;
    private Button openPhoto;

    CommentHolder(@NonNull View itemView) {
        super(itemView);
        rate = itemView.findViewById(R.id.ratingBar4);
        img = itemView.findViewById(R.id.ImgRestaurant);
        comment = itemView.findViewById(R.id.comment);
        imageView=itemView.findViewById(R.id.imageView);
        openPhoto = itemView.findViewById(R.id.openPhoto);
    }

    RatingBar getRate() {
        return rate;
    }

    ImageView getImg() {
        return img;
    }

    TextView getComment() {
        return comment;
    }

    ImageView getImageView(){return imageView;}

    public Button getOpenPhoto() {
        return openPhoto;
    }
}
