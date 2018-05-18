package com.android.sgvn.gymme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sgvn144 on 2018/04/10.
 */

public class Meal  {
    @SerializedName("imageURL")
    @Expose
    private String imageURL;

    @SerializedName("mealName")
    @Expose
    private String mealName;

    public Meal() {
    }

    public Meal(String imageURL, String mealName) {
        this.imageURL = imageURL;
        this.mealName = mealName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }
}
