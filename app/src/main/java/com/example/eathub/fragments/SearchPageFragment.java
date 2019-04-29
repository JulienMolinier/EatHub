package com.example.eathub.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.eathub.R;


public class SearchPageFragment extends Fragment {

    public View view;

    public SearchPageFragment() {
        // Required empty public constructor
    }

    public String search;
    public TextView text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.search_page_layout, container,false);

        search = getArguments().getString("params");
        Button button = view.findViewById(R.id.buttonBackSearch);
        text = view.findViewById(R.id.aff);
        // recherche effectuee
        text.setText(search);

        final Activity activity = getActivity();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        return view;
    }
}


