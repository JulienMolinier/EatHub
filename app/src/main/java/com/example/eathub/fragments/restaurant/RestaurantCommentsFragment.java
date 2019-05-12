package com.example.eathub.fragments.restaurant;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.eathub.R;
import com.example.eathub.adapters.CommentRVAdapter;
import com.example.eathub.models.ProfileModel;
import com.example.eathub.models.RestaurantModel;
import com.example.eathub.models.VisitModel;
import com.example.eathub.models.databases.DatabaseHandler;
import com.example.eathub.models.databases.VisitDatabase;

import java.time.LocalDate;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class RestaurantCommentsFragment extends Fragment {
    private View view;
    private RecyclerView listComments;
    private ProfileModel profileModel;
    private RestaurantModel restaurantModel;
    private Button addACommentButton;
    private CommentRVAdapter commentRVAdapter;
    private ArrayList<VisitModel> commentList = new ArrayList<>();
    private ImageView imageView;
    private Bitmap imageBitmap;

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

        Dialog popup = new Dialog(getContext());
        addACommentButton.setOnClickListener((View v) -> {
            popup.setContentView(R.layout.addcomment);

            Button addFriendButton = popup.findViewById(R.id.commentButton);
            Button cancelButton = popup.findViewById(R.id.cancelButton);
            RatingBar rateInput = popup.findViewById(R.id.ratingBar3);
            EditText commentInput = popup.findViewById(R.id.commentInput);
            DatePicker dateInput = popup.findViewById(R.id.datePicker);
            EditText priceInput = popup.findViewById(R.id.priceInput);
            EditText caloriesInput = popup.findViewById(R.id.caloriesInput);
            imageView=popup.findViewById(R.id.imageView);
            ImageButton button_image = popup.findViewById(R.id.button_image);

            button_image.setOnClickListener(view ->{
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
            });


            cancelButton.setOnClickListener((View v1) -> popup.dismiss());

            addFriendButton.setOnClickListener((View v2) -> {
                if (rateInput.getRating() != 0.0 && commentInput.getText() != null
                        && caloriesInput.getText() != null) {
                    int day = dateInput.getDayOfMonth();
                    int month = dateInput.getMonth() + 1;
                    int year = dateInput.getYear();
                    VisitModel visitToAdd = new VisitModel(profileModel, restaurantModel,
                            LocalDate.of(year, month, day),
                            Double.valueOf(caloriesInput.getText().toString()),
                            Double.valueOf(priceInput.getText().toString()),
                            commentInput.getText().toString(),
                            (double) rateInput.getRating());
                    //visitToAdd.setImageBitmap(imageBitmap);
                    DatabaseHandler.addVisitToDB(visitToAdd);
                    VisitDatabase.getVisits().add(visitToAdd);
                    getCommentList();
                    commentRVAdapter.notifyDataSetChanged();
                }
                popup.dismiss();
            });
            popup.show();
        });

        getCommentList();
        commentRVAdapter = new CommentRVAdapter(this.getContext(), commentList);
        listComments.setAdapter(commentRVAdapter);

        return view;
    }

    public void setProfileModel(ProfileModel profileModel) {
        this.profileModel = profileModel;
    }

    public void setRestaurantModel(RestaurantModel restaurantModel) {
        this.restaurantModel = restaurantModel;
    }

    private void getCommentList() {
        commentList.clear();
        for (VisitModel visit : VisitDatabase.getVisits()) {
            if (visit.getRestaurant().equals(this.restaurantModel)) {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }
}
