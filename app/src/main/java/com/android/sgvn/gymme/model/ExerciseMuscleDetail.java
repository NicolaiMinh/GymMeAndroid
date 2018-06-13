package com.android.sgvn.gymme.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sgvn144 on 2018/05/11.
 */

public class ExerciseMuscleDetail implements Parcelable {

    @SerializedName("execution")
    @Expose
    private String execution;

    @SerializedName("exerciseDetail")
    @Expose
    private String exerciseDetail;

    @SerializedName("exerciseName")
    @Expose
    private String exerciseName;

    @SerializedName("imageURL")
    @Expose
    private String imageURL;

    @SerializedName("preparation")
    @Expose
    private String preparation;

    @SerializedName("primaryMuscle")
    @Expose
    private String primaryMuscle;

    @SerializedName("secondaryMucsle")
    @Expose
    private String secondaryMucsle;


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

    public ExerciseMuscleDetail( String execution, String exerciseDetail, String exerciseName, String imageURL, String preparation, String primaryMuscle, String secondaryMucsle, String videoURL, boolean favorite, int id) {
        this.execution = execution;
        this.exerciseDetail = exerciseDetail;
        this.exerciseName = exerciseName;
        this.imageURL = imageURL;
        this.preparation = preparation;
        this.primaryMuscle = primaryMuscle;
        this.secondaryMucsle = secondaryMucsle;
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

    public String getExecution() {
        return execution;
    }

    public void setExecution(String execution) {
        this.execution = execution;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public String getPrimaryMuscle() {
        return primaryMuscle;
    }

    public void setPrimaryMuscle(String primaryMuscle) {
        this.primaryMuscle = primaryMuscle;
    }

    public String getSecondaryMucsle() {
        return secondaryMucsle;
    }

    public void setSecondaryMucsle(String secondaryMucsle) {
        this.secondaryMucsle = secondaryMucsle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(execution);
        dest.writeString(exerciseDetail);
        dest.writeString(exerciseName);
        dest.writeString(imageURL);
        dest.writeString(preparation);
        dest.writeString(primaryMuscle);
        dest.writeString(secondaryMucsle);
        dest.writeString(videoURL);
        dest.writeInt(favorite ? 1 : 0);
        dest.writeInt(id);
    }
    public static final Parcelable.Creator<ExerciseMuscleDetail> CREATOR
            = new Parcelable.Creator<ExerciseMuscleDetail>() {
        public ExerciseMuscleDetail createFromParcel(Parcel in) {
            return new ExerciseMuscleDetail(in);
        }

        public ExerciseMuscleDetail[] newArray(int size) {
            return new ExerciseMuscleDetail[size];
        }
    };

    private ExerciseMuscleDetail(Parcel in) {
        execution = in.readString();
        exerciseDetail = in.readString();
        exerciseName = in.readString();
        imageURL = in.readString();
        preparation = in.readString();
        primaryMuscle = in.readString();
        secondaryMucsle = in.readString();
        videoURL = in.readString();
        favorite = (in.readInt() == 0) ? false : true;
        id = in.readInt();
    }
}
