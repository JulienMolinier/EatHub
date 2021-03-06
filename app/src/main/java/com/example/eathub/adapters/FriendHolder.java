package com.example.eathub.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.eathub.R;
import com.github.abdularis.civ.CircleImageView;

class FriendHolder extends RecyclerView.ViewHolder {
    private CircleImageView avatarFriend;
    private TextView nameFriend;

    FriendHolder(@NonNull View itemView) {
        super(itemView);
        avatarFriend = itemView.findViewById(R.id.avatarFriend);
        nameFriend = itemView.findViewById(R.id.nameFriend);
    }

    CircleImageView getAvatarFriend() {
        return avatarFriend;
    }

    TextView getNameFriend() {
        return nameFriend;
    }
}
