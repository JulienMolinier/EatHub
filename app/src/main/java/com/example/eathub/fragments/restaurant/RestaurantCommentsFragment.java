package com.example.eathub.fragments.restaurant;

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

public class RestaurantCommentsFragment extends Fragment {
    private View view;
    private ListView detailsListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.restaurantcomments, container, false);
        detailsListView = view.findViewById(R.id.detailsListView);
        Button addACommentButton = view.findViewById(R.id.addACommentButton);

        return view;
    }
}
