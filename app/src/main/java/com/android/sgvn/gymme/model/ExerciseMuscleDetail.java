package com.android.sgvn.gymme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sgvn144 on 2018/05/11.
 */

public class ExerciseMuscleDetail {
    @SerializedName("exerciseDetail")
    @Expose
    private String exerciseDetail;

    @SerializedName("exerciseName")
    @Expose
    private String exerciseName;

    @SerializedName("imageURL")
    @Expose
    private String imageURL;

    @SerializedName("videoURL")
    @Expose
    private String videoURL;

    private int color;

    @SerializedName("isFavourite")
    @Expose
    private boolean isFavourite;


    public ExerciseMuscleDetail() {
    }

    public ExerciseMuscleDetail(String exerciseDetail, String exerciseName, String imageURL, boolean isFavourite, String videoURL) {
        this.exerciseDetail = exerciseDetail;
        this.exerciseName = exerciseName;
        this.imageURL = imageURL;
        this.isFavourite = isFavourite;
        this.videoURL = videoURL;
    }

    public String getExerciseDetail() {
        return exerciseDetail;
    }

    public void setExerciseDetail(String exerciseDetail) {
        this.exerciseDetail = exerciseDetail;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
}
