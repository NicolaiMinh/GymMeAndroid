package com.android.sgvn.gymme.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.adapter.ExerciseMuscleRecyclerAdapter;
import com.android.sgvn.gymme.common.Common;
import com.android.sgvn.gymme.model.ExerciseMuscleDetail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ExerciseMuscleActivity extends AppCompatActivity implements ExerciseMuscleRecyclerAdapter.ExerciseMuscleRecyclerHolder.ClickListener {
    private static final String TAG = ExerciseMuscleActivity.class.getSimpleName();

    @BindView(R.id.list_muscle_exercise)
    RecyclerView listMuscleExercise;

    String idExercise, nameExercise;

    private ExerciseMuscleRecyclerAdapter mAdapter;
    private List<ExerciseMuscleDetail> exerciseMuscleDetailList;

    //firebase
    FirebaseDatabase database;
    DatabaseReference reference;


    private int positionheart = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_muscle);
        ButterKnife.bind(this);

        idExercise = getIntent().getStringExtra("idExercise");
        nameExercise = getIntent().getStringExtra("nameExercise");
        Log.d(TAG, idExercise + " " + nameExercise);


        database = FirebaseDatabase.getInstance();
        reference = database.getReference(Common.FIREBASE_MUSCLE_EXERCISE_TABLE);

        initView();


    }

    private void initView() {
        exerciseMuscleDetailList = new ArrayList<>();
        exerciseMuscleDetailList.clear();
        mAdapter = new ExerciseMuscleRecyclerAdapter(this, exerciseMuscleDetailList, this);
        listMuscleExercise.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);//hien thi 2 cot
        listMuscleExercise.setLayoutManager(gridLayoutManager);
        listMuscleExercise.setAdapter(mAdapter);
        if (nameExercise != null) {
            getDataFromFirebase();
        }

    }

    private void getDataFromFirebase() {
        if (nameExercise.equals("Chest")) {
            reference.child(Common.FIREBASE_MUSCLE_EXERCISE_CHEST_TABLE).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ExerciseMuscleDetail muscleDetail = snapshot.getValue(ExerciseMuscleDetail.class);

                        exerciseMuscleDetailList.add(muscleDetail);
                    }
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, "Database error: " + databaseError.getMessage());
                }
            });
        } else if (nameExercise.equals("Leg")) {
            reference.child(Common.FIREBASE_MUSCLE_EXERCISE_LEG_TABLE).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ExerciseMuscleDetail muscleDetail = snapshot.getValue(ExerciseMuscleDetail.class);

                        exerciseMuscleDetailList.add(muscleDetail);
                    }
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, "Database error: " + databaseError.getMessage());
                }
            });
        } else if (nameExercise.equals("Shoulder")) {
            reference.child(Common.FIREBASE_MUSCLE_EXERCISE_SHOULDER_TABLE).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ExerciseMuscleDetail muscleDetail = snapshot.getValue(ExerciseMuscleDetail.class);

                        exerciseMuscleDetailList.add(muscleDetail);
                    }
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, "Database error: " + databaseError.getMessage());
                }
            });
        }

    }


    //implements ExerciseMuscleRecyclerAdapter.ExerciseMuscleRecyclerHolder.ClickListener
    @Override
    public void onCLickItem(final int position) {
        Log.d(TAG, "position " + mAdapter.getExerciseMuscleDetail().get(position));
    }


}
