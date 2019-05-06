package com.example.eathub.fragments.profile;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.eathub.R;
import com.example.eathub.models.ProfileModel;
import com.example.eathub.models.VisitModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class ProfileChartsFragment extends Fragment {
    private View view;
    private ProfileModel profileModel;
    private BarChart chart1;
    private BarChart chart2;
    private Spinner spinner;
    private TextView text;
    private TextView text2;
    private ArrayAdapter<CharSequence> adapter;

    private int spinnerChoice;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profilecharts, container, false);
        chart1 = view.findViewById(R.id.chart1);
        chart2 = view.findViewById(R.id.chart2);
        text = view.findViewById(R.id.textView);
        text2 = view.findViewById(R.id.textView2);

        text.setText(R.string.caloriesEvolution);
        text2.setText(R.string.budgetEvolution);

        spinner = view.findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.choice_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        spinnerChoice = position;
                        break;
                    case 1:
                        spinnerChoice = position;
                        break;
                    case 2:
                        spinnerChoice = position;
                        break;
                    case 3:
                        spinnerChoice = position;
                        break;
                }
                profileModel.computeValues(spinnerChoice);
                setGraph(spinnerChoice);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerChoice = 0;
                profileModel.computeValues(spinnerChoice);
                setGraph(spinnerChoice);
            }
        });

        return view;
    }

    private void setGraph(int spinnerChoice) {
        List<BarEntry> entries = new ArrayList<>();
        List<BarEntry> entries2 = new ArrayList<>();
        chart1.clear();
        chart2.clear();

        if (!this.profileModel.getHistory().isEmpty()) {
            for (VisitModel visit : this.profileModel.getHistory()) {
                if (spinnerChoice == 0 || spinnerChoice == 1 || spinnerChoice == 2) {
                    boolean done = false;
                    for (BarEntry entry : entries) {
                        if (entry.getX() == visit.getDate().getDayOfMonth()) {
                            float old = entry.getY();
                            old += visit.getCalories();
                            entry.setY(old);

                            old = entries2.get(entries.indexOf(entry)).getY();
                            old += visit.getPrice();
                            entries2.get(entries.indexOf(entry)).setY(old);
                            done = true;
                        }
                    }
                    if (!done) {
                        entries.add(new BarEntry((float) visit.getDate().getDayOfMonth(), (float) visit.getCalories()));
                        entries2.add(new BarEntry((float) visit.getDate().getDayOfMonth(), (float) visit.getPrice()));
                    }

                } else {
                    boolean done = false;
                    for (BarEntry entry : entries) {
                        if (entry.getX() == visit.getDate().getDayOfYear()) {
                            float old = entry.getY();
                            old += visit.getCalories();
                            entry.setY(old);

                            old = entries2.get(entries.indexOf(entry)).getY();
                            old += visit.getPrice();
                            entries2.get(entries.indexOf(entry)).setY(old);
                            done = true;
                        }
                    }
                    if (!done) {
                        entries.add(new BarEntry((float) visit.getDate().getDayOfYear(), (float) visit.getCalories()));
                        entries2.add(new BarEntry((float) visit.getDate().getDayOfYear(), (float) visit.getPrice()));
                    }
                }
            }

            BarDataSet data = new BarDataSet(entries, "Calories");
            data.setColor(Color.parseColor("#ffaa04"));
            BarData barData = new BarData(data);
            chart1.setData(barData);
            chart1.getXAxis().setDrawLabels(true);
            chart1.getAxisRight().setEnabled(false);
            chart1.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

            BarDataSet data2 = new BarDataSet(entries2, "Budget");
            data2.setColor(Color.parseColor("#ffaa04"));
            BarData barData2 = new BarData(data2);
            chart2.setData(barData2);
            chart2.getXAxis().setDrawLabels(true);
            chart2.getAxisRight().setEnabled(false);
            chart2.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        }

        chart1.getDescription().setText("");
        chart1.setNoDataText("No visits available to this date !");
        chart1.invalidate();
        chart2.getDescription().setText("");
        chart2.setNoDataText("No visits available to this date !");
        chart2.invalidate();
    }

    public void setProfile(ProfileModel profile) {
        this.profileModel = profile;
    }
}
