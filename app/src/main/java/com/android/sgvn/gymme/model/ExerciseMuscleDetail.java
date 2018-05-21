package com.android.sgvn.gymme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

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

    @SerializedName("favorite")
    @Expose
    private boolean favorite;

    @SerializedName("id")
    @Expose
    private int id;


    public ExerciseMuscleDetail() {
    }

    public ExerciseMuscleDetail(String exerciseDetail, String exerciseName, String imageURL, String videoURL, boolean favorite, int id) {
        this.exerciseDetail = exerciseDetail;
        this.exerciseName = exerciseName;
        this.imageURL = imageURL;
        this.videoURL = videoURL;
        this.favorite = favorite;
        this.id = id;
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

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
