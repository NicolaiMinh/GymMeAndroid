package com.android.sgvn.gymme.model;

/**
 * Created by sgvn144 on 2018/05/11.
 */

public class ExerciseMuscle {
    private String id;
    private String exerciseMuscleName;

    public ExerciseMuscle(String id, String exerciseMuscleName) {
        this.id = id;
        this.exerciseMuscleName = exerciseMuscleName;
    }

    public ExerciseMuscle() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExerciseMuscleName() {
        return exerciseMuscleName;
    }

    public void setExerciseMuscleName(String exerciseMuscleName) {
        this.exerciseMuscleName = exerciseMuscleName;
    }
}
