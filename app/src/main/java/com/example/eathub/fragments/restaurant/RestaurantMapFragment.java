package com.example.eathub.fragments.restaurant;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.eathub.R;
import com.example.eathub.models.RestaurantModel;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

public class RestaurantMapFragment extends Fragment {
    private RelativeLayout view;
    private RestaurantModel theRestaurant;
    private MapView map;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (RelativeLayout) inflater.inflate(R.layout.restaurantmap, container, false);

        map = view.findViewById(R.id.map);

        Configuration.getInstance().load(getActivity(), PreferenceManager.getDefaultSharedPreferences(getActivity()));
        map.setBuiltInZoomControls(true);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setClickable(true);
        map.setMultiTouchControls(true);
        map.getController().setZoom(15.0);
        GeoPoint startPoint = new GeoPoint(43.6167, 7.0747800000000325);
        map.getController().setCenter(startPoint);

        ArrayList<OverlayItem> list = new ArrayList<>();
        OverlayItem overlay = new OverlayItem("", "",startPoint);

        return view;
    }

    public void setTheRestaurant(RestaurantModel theRestaurant) {
        this.theRestaurant = theRestaurant;
    }
}
