package com.example.eathub.models;

import java.time.LocalDate;

public class CommentModel {
    private ProfileModel profileModel;
    private int rate;
    private String comment;
    private LocalDate date;

    public CommentModel( ProfileModel profileModel, int rate, String comment){
        this.comment=comment;
        this.profileModel=profileModel;
        this.rate=rate;
        this.date=LocalDate.now();
    }

    public int getRate() {
        return rate;
    }

    public String getComment() {
        return comment;
    }

    public LocalDate getDate() {
        return date;
    }

    public ProfileModel getProfileModel() {
        return profileModel;
    }
}
