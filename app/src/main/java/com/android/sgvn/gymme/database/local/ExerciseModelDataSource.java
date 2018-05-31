//package com.android.sgvn.gymme.database.local;
//
//import com.android.sgvn.gymme.database.datasource.IExerciseModelDataSource;
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
//public class ExerciseModelDataSource implements IExerciseModelDataSource {
//
//    private ExerciseModelDAO exerciseModelDAO;
//    private static ExerciseModelDataSource instance;
//
//    public ExerciseModelDataSource(ExerciseModelDAO exerciseModelDAO) {
//        this.exerciseModelDAO = exerciseModelDAO;
//    }
//
//    public static ExerciseModelDataSource getInstance(ExerciseModelDAO exerciseModelDAO) {
//        if (instance == null) {
//            instance = new ExerciseModelDataSource(exerciseModelDAO);
//        }
//        return instance;
//    }
//
//    @Override
//    public Flowable<List<ExerciseModel>> getExerciseModelItems() {
//        return exerciseModelDAO.getExerciseModelItems();
//    }
//
//    @Override
//    public Flowable<List<ExerciseModel>> getExerciseModelItemsById(int exerciseModelID) {
//        return exerciseModelDAO.getExerciseModelItemsById(exerciseModelID);
//    }
//
//    @Override
//    public int countExerciseModelItems() {
//        return exerciseModelDAO.countExerciseModelItems();
//    }
//
//    @Override
//    public void deleteExerciseModel() {
//        exerciseModelDAO.deleteExerciseModel();
//    }
//
//    @Override
//    public void insertToExerciseModel(ExerciseModel... exerciseModels) {
//        exerciseModelDAO.insertToExerciseModel(exerciseModels);
//    }
//
//    @Override
//    public void updateExerciseModel(ExerciseModel... exerciseModels) {
//        exerciseModelDAO.updateExerciseModel(exerciseModels);
//    }
//
//    @Override
//    public void deleteExerciseModelItem(ExerciseModel exerciseModel) {
//        exerciseModelDAO.deleteExerciseModelItem(exerciseModel);
//    }
//}
