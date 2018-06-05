package com.android.sgvn.gymme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sgvn144 on 2018/06/05.
 */

public class ExerciseSchedule {
    @SerializedName("dayPresent")
    @Expose
    private String dayPresent;

    @SerializedName("exerciseID")
    @Expose
    private int exerciseID;

    @SerializedName("muscleExercise")
    @Expose
    private String muscleExercise;//mapping vs table MuscleExercises/Chest...

    @SerializedName("muscleID")
    @Expose
    private int muscleID;//mapping vs table MuscleExercises

    public ExerciseSchedule() {
    }

    public ExerciseSchedule(String dayPresent, int exerciseID, String muscleExercise, int muscleID) {
        this.dayPresent = dayPresent;
        this.exerciseID = exerciseID;
        this.muscleExercise = muscleExercise;
        this.muscleID = muscleID;
    }

    public String getDayPresent() {
        return dayPresent;
    }

    public void setDayPresent(String dayPresent) {
        this.dayPresent = dayPresent;
    }

    public int getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(int exerciseID) {
        this.exerciseID = exerciseID;
    }

    public String getMuscleExercise() {
        return muscleExercise;
    }

    public void setMuscleExercise(String muscleExercise) {
        this.muscleExercise = muscleExercise;
    }

    public int getMuscleID() {
        return muscleID;
    }

    public void setMuscleID(int muscleID) {
        this.muscleID = muscleID;
    }
}
