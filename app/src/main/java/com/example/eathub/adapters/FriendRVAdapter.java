package com.example.eathub.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eathub.R;
import com.example.eathub.activities.FriendProfileActivity;
import com.example.eathub.models.ProfileModel;
import com.example.eathub.models.RestaurantModel;

import java.util.List;

public class FriendRVAdapter extends RecyclerView.Adapter<FriendHolder> {

    private List<ProfileModel> friendList;
    private Context context;

    public FriendRVAdapter(Context context, List<ProfileModel> friendList){
        this.context = context;
        this.friendList = friendList;
    }
    @NonNull
    @Override
    public FriendHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.friend_list_item, viewGroup, false);

        final Intent myIntent = new Intent();
        ProfileModel myFriend = friendList.get(i);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myIntent.putExtra("profilepicked", myFriend);
                context.startActivity(myIntent);
            }
        });
        return new FriendHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FriendHolder viewHolder, int i) {
        ProfileModel myFriend = friendList.get(i);
        viewHolder.getAvatarFriend().setImageResource( context.getResources().getIdentifier(myFriend.getName()
                .replaceAll(" ", "").replaceAll("-", "").toLowerCase(), "drawable", context.getPackageName()));
        viewHolder.getNameFriend().setText(myFriend.getFirstName());

        Intent myIntent = new Intent(context.getApplicationContext(), FriendProfileActivity.class);
        viewHolder.getAvatarFriend().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myIntent.putExtra("profilepicked", myFriend);
                context.startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }
}
