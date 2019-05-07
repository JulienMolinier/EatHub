package com.example.eathub.fragments.restaurant;

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
import org.osmdroid.views.overlay.Marker;


public class RestaurantMapFragment extends Fragment {
    private RelativeLayout view;
    private RestaurantModel theRestaurant;
    private MapView map;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (RelativeLayout) inflater.inflate(R.layout.restaurantmap, container, false);
        if (savedInstanceState != null){
            theRestaurant = savedInstanceState.getParcelable("currentRestaurant");
        }
        initMap();
        return view;
    }

    public void setTheRestaurant(RestaurantModel theRestaurant) {
        this.theRestaurant = theRestaurant;
    }

    public void initMap() {
        map = view.findViewById(R.id.map);
        Configuration.getInstance().load(getActivity(), PreferenceManager.getDefaultSharedPreferences(getActivity()));
        map.setBuiltInZoomControls(true);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setClickable(true);
        map.setMultiTouchControls(true);
        map.getController().setZoom(17.0);
        GeoPoint startPoint = new GeoPoint(theRestaurant.getLatitude(), theRestaurant.getLongitude());
        map.getController().setCenter(startPoint);

        Marker tec = new Marker(map);
        tec.setPosition(startPoint);
        tec.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        tec.setTitle(theRestaurant.getName());
        map.getOverlays().add(tec);

        map.invalidate();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the state
        savedInstanceState.putParcelable("currentRestaurant", theRestaurant);
        super.onSaveInstanceState(savedInstanceState);

    }
}
