package com.example.eathub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eathub.R;
import com.example.eathub.models.VisitModel;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class HistoryAdapter extends BaseAdapter {

    private Context context;
    private List<VisitModel> history;

    public HistoryAdapter(Context context, List<VisitModel> history) {
        this.context = context;
        this.history = history;
    }

    @Override
    public int getCount() {
        return this.history.size();
    }

    @Override
    public Object getItem(int position) {
        return this.history.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.history_list_item, null);
        }
        VisitModel visit = (VisitModel) this.getItem(position);
        TextView nameRestaurant = view.findViewById(R.id.nameRestaurant);
        nameRestaurant.setText(visit.getRestaurant().getName());

        ImageView avatarRestaurant = view.findViewById(R.id.avatarRestaurant);
        int avatar = (view.getResources().getIdentifier(visit.getRestaurant()
                .getName().replaceAll(" ", "").toLowerCase(), "drawable", context.getPackageName()));
        avatarRestaurant.setImageResource(avatar);

        TextView date = view.findViewById(R.id.date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        date.setText(visit.getDate().format(formatter));

        TextView price = view.findViewById(R.id.priceVisit);
        price.setText(visit.getPrice() + " €");
        return view;
    }
}
