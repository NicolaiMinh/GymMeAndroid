//package com.android.sgvn.gymme.database.local;
//
//import android.arch.persistence.db.SupportSQLiteOpenHelper;
//import android.arch.persistence.room.Database;
//import android.arch.persistence.room.DatabaseConfiguration;
//import android.arch.persistence.room.InvalidationTracker;
//import android.arch.persistence.room.Room;
//import android.arch.persistence.room.RoomDatabase;
//import android.content.Context;
//import android.support.annotation.NonNull;
//
//import com.android.sgvn.gymme.database.modelDB.ExerciseModel;
//
///**
// * Created by sgvn144 on 2018/05/31.
// */
//
//@Database(entities = {ExerciseModel.class}, version = 1)
//public abstract class ExerciseModelDatabase extends RoomDatabase {
//
//    public abstract ExerciseModelDAO exerciseModelDAO();
//
//    private static ExerciseModelDatabase instance;
//
//    public static ExerciseModelDatabase getInstance(Context context) {
//        if (instance == null) {
//            instance = Room.databaseBuilder(context, ExerciseModelDatabase.class, "GymMe")
//                    .allowMainThreadQueries()
//                    .build();
//        }
//        return instance;
//    }
//}
