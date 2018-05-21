package com.android.sgvn.gymme.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.adapter.ExerciseMuscleRecyclerAdapter;
import com.android.sgvn.gymme.common.Common;
import com.android.sgvn.gymme.fragments.tabMainFragments.FavoriteEachExerciseFragment;
import com.android.sgvn.gymme.model.ExerciseMuscleDetail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ExerciseMuscleActivity extends AppCompatActivity implements ExerciseMuscleRecyclerAdapter.ExerciseMuscleRecyclerHolder.ClickListener {
    private static final String TAG = ExerciseMuscleActivity.class.getSimpleName();

    @BindView(R.id.list_muscle_exercise)
    RecyclerView listMuscleExercise;
    @BindView(R.id.favoriteEachExercise)
    ImageView favoriteEachExercise;
    @BindView(R.id.favoriteEachExerciseFragment)
    FrameLayout favoriteEachExerciseFragment;

    private ExerciseMuscleRecyclerAdapter mAdapter;
    private List<ExerciseMuscleDetail> exerciseMuscleDetailList;
    private boolean isSetFavorite;
    private ExerciseMuscleDetail currentPosition;
    String idExercise, nameExercise;

    //firebase
    FirebaseDatabase database;
    DatabaseReference reference;

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
                        Log.d(TAG, String.valueOf(muscleDetail.isFavorite()));
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
    public void onClickItem(final int position) {
        Log.d(TAG, "position " + mAdapter.getExerciseMuscleDetail().get(position));
    }

    //click item favorite
    @Override
    public void onClickFavoriteItem(int position) {
        if (mAdapter.getExerciseMuscleDetail().get(position).isFavorite()) {
            mAdapter.getExerciseMuscleDetail().get(position).setFavorite(false);
            isSetFavorite = false;
        } else {
            mAdapter.getExerciseMuscleDetail().get(position).setFavorite(true);
            isSetFavorite = true;
        }
        currentPosition = mAdapter.getExerciseMuscleDetail().get(position);
        uploadSetFavorite(isSetFavorite);
        mAdapter.notifyDataSetChanged();
        Log.d(TAG, "position favorite click " + mAdapter.getExerciseMuscleDetail().get(position));
    }

    private void uploadSetFavorite(final boolean isSetFavorite) {
        HashMap<String, Object> params = new HashMap<>();
        params.put(Common.EXERCISE_SET_FAVORITE_PROPERTY, isSetFavorite);
        // get current position by ID and update
        if (!nameExercise.isEmpty()) {
            if (nameExercise.equals("Chest")) {
                reference.child(Common.FIREBASE_MUSCLE_EXERCISE_CHEST_TABLE).child(String.valueOf(currentPosition.getId())).updateChildren(params);
                mAdapter.notifyDataSetChanged();
            } else if (nameExercise.equals("Leg")) {
                reference.child(Common.FIREBASE_MUSCLE_EXERCISE_LEG_TABLE).child(String.valueOf(currentPosition.getId())).updateChildren(params);
                mAdapter.notifyDataSetChanged();
            } else if (nameExercise.equals("Shoulder")) {
                reference.child(Common.FIREBASE_MUSCLE_EXERCISE_SHOULDER_TABLE).child(String.valueOf(currentPosition.getId())).updateChildren(params);
                mAdapter.notifyDataSetChanged();
            }
        }
    }


    @OnClick({R.id.favoriteEachExercise})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.favoriteEachExercise:
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FavoriteEachExerciseFragment favoriteEachExerciseFragment = new FavoriteEachExerciseFragment();
                fragmentTransaction.add(R.id.fragment, favoriteEachExerciseFragment);
                fragmentTransaction.commit();
                break;

        }
    }

}
