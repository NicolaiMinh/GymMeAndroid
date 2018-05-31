//package com.android.sgvn.gymme.database.datasource;
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
//public interface IExerciseModelDataSource {
//    Flowable<List<ExerciseModel>> getExerciseModelItems();
//
//    Flowable<List<ExerciseModel>> getExerciseModelItemsById(int exerciseModelID);
//
//    int countExerciseModelItems();
//
//    void deleteExerciseModel();
//
//    void insertToExerciseModel(ExerciseModel... exerciseModels);
//
//    void updateExerciseModel(ExerciseModel... exerciseModels);
//
//    void deleteExerciseModelItem(ExerciseModel exerciseModel);
//}
