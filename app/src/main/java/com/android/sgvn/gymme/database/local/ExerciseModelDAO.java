//package com.android.sgvn.gymme.database.local;
//
//import android.arch.persistence.room.Dao;
//import android.arch.persistence.room.Delete;
//import android.arch.persistence.room.Insert;
//import android.arch.persistence.room.Query;
//import android.arch.persistence.room.Update;
//
//import com.android.sgvn.gymme.database.modelDB.ExerciseModel;
//
//import java.util.List;
//
//import io.reactivex.Flowable;
//
///**
// * Created by sgvn144 on 2018/05/31.
// */
//
//@Dao
//public interface ExerciseModelDAO {
//
//    @Query("SELECT * FROM ExerciseModel")
//    Flowable<List<ExerciseModel>> getExerciseModelItems();
//
//    @Query("SELECT * FROM ExerciseModel WHERE id=:exerciseModelID")
//    Flowable<List<ExerciseModel>> getExerciseModelItemsById(int exerciseModelID);
//
//    @Query("SELECT COUNT(*) FROM ExerciseModel")
//    int countExerciseModelItems();
//
//    @Query("DELETE FROM ExerciseModel")
//    void deleteExerciseModel();
//
//    @Insert
//    void insertToExerciseModel(ExerciseModel...exerciseModels);
//
//    @Update
//    void updateExerciseModel(ExerciseModel...exerciseModels);
//
//    @Delete
//    void deleteExerciseModelItem(ExerciseModel exerciseModel);
//}
