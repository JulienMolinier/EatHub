package com.example.eathub.fragments.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.eathub.R;
import com.example.eathub.activities.CommentActivity;
import com.example.eathub.adapters.CommentListAdapter;
import com.example.eathub.models.ProfileModel;
import com.example.eathub.models.RestaurantModel;
import com.example.eathub.models.VisitModel;
import com.example.eathub.models.databases.VisitDatabase;

import java.util.ArrayList;

public class RestaurantCommentsFragment extends Fragment {
    private View view;
    private ListView listComments;
    private ProfileModel profileModel;
    private RestaurantModel restaurantModel;
    private Button addACommentButton;
    private CommentListAdapter myCommentListAdapter;
    private ArrayList<VisitModel> commentList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.restaurantcomments, container, false);
        listComments = view.findViewById(R.id.listComments);
        addACommentButton = view.findViewById(R.id.addACommentButton);
        if (savedInstanceState != null) {
            restaurantModel = savedInstanceState.getParcelable("currentRestaurant");
            profileModel = savedInstanceState.getParcelable("currentProfile");
        }

        addACommentButton.setOnClickListener((View v) -> {
            Intent intent = new Intent(getContext(), CommentActivity.class);
            intent.putExtra("currentProfile", profileModel);
            intent.putExtra("currentRestaurant", restaurantModel);
            startActivity(intent);

        });
        ListView listComments = view.findViewById(R.id.listComments);
        getCommentList();
        if(!commentList.isEmpty()) {
            myCommentListAdapter = new CommentListAdapter(this.getContext(), commentList);
            listComments.setAdapter(myCommentListAdapter);
        }
        return view;
    }

    public void setProfileModel(ProfileModel profileModel) {
        this.profileModel = profileModel;
    }

    public void setRestaurantModel(RestaurantModel restaurantModel) {
        this.restaurantModel = restaurantModel;
    }

    private void getCommentList(){
        for(VisitModel visit : VisitDatabase.getVisits()){
            if (visit.getRestaurant().equals(this.restaurantModel)){
                commentList.add(visit);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the state
        savedInstanceState.putParcelable("currentRestaurant", restaurantModel);
        savedInstanceState.putParcelable("currentProfile", profileModel);

        super.onSaveInstanceState(savedInstanceState);

    }
}
