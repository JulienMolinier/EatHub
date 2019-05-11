package com.example.eathub.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.eathub.R;
import com.example.eathub.models.ProfileModel;
import com.github.abdularis.civ.CircleImageView;

import java.util.List;

public class FriendSpinnerAdapter extends ArrayAdapter<ProfileModel> {
    private Context context;
    private List<ProfileModel> users;
    private int resource;
    public FriendSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<ProfileModel> users) {
        super(context, resource, users);
        this.context = context;
        this.resource = resource;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Nullable
    @Override
    public ProfileModel getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,  @NonNull ViewGroup parent) {
        return myView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position,  @Nullable View convertView, @NonNull ViewGroup parent) {
        return myView(position, convertView, parent);
    }

    private View myView(int position, View convertView, ViewGroup parent){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.add_friend_spinner, parent, false);
        CircleImageView userPic = convertView.findViewById(R.id.userPic);
        TextView userName = convertView.findViewById(R.id.userName);
        ProfileModel myUser = users.get(position);

        userPic.setImageResource(context.getResources().getIdentifier(myUser.getName()
                .replaceAll(" ", "").replaceAll("-", "").toLowerCase(), "drawable", context.getPackageName()));

        userName.setText(myUser.getFirstName());
        return convertView;
    }
}
