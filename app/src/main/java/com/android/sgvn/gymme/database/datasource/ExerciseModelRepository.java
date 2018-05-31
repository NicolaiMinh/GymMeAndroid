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
//public class ExerciseModelRepository implements IExerciseModelDataSource {
//
//    private IExerciseModelDataSource iExerciseModelDataSource;
//
//    private static ExerciseModelRepository instance;
//
//    public ExerciseModelRepository(IExerciseModelDataSource iExerciseModelDataSource) {
//        this.iExerciseModelDataSource = iExerciseModelDataSource;
//    }
//
//    public static ExerciseModelRepository getInstance(IExerciseModelDataSource iExerciseModelDataSource) {
//        if (instance == null) {
//            instance = new ExerciseModelRepository(iExerciseModelDataSource);
//        }
//        return instance;
//    }
//
//    @Override
//    public Flowable<List<ExerciseModel>> getExerciseModelItems() {
//        return iExerciseModelDataSource.getExerciseModelItems();
//    }
//
//    @Override
//    public Flowable<List<ExerciseModel>> getExerciseModelItemsById(int exerciseModelID) {
//        return iExerciseModelDataSource.getExerciseModelItemsById(exerciseModelID);
//    }
//
//    @Override
//    public int countExerciseModelItems() {
//        return iExerciseModelDataSource.countExerciseModelItems();
//    }
//
//    @Override
//    public void deleteExerciseModel() {
//        iExerciseModelDataSource.deleteExerciseModel();
//    }
//
//    @Override
//    public void insertToExerciseModel(ExerciseModel... exerciseModels) {
//        iExerciseModelDataSource.insertToExerciseModel(exerciseModels);
//    }
//
//    @Override
//    public void updateExerciseModel(ExerciseModel... exerciseModels) {
//        iExerciseModelDataSource.updateExerciseModel(exerciseModels);
//    }
//
//    @Override
//    public void deleteExerciseModelItem(ExerciseModel exerciseModel) {
//        iExerciseModelDataSource.deleteExerciseModelItem(exerciseModel);
//    }
//}
