package com.android.sgvn.gymme.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.adapter.ExerciseMuscleFavoriteRecyclerAdapter;
import com.android.sgvn.gymme.adapter.ExerciseMuscleRecyclerAdapter;
import com.android.sgvn.gymme.common.Common;
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


public class ExerciseMuscleActivity extends AppCompatActivity implements ExerciseMuscleRecyclerAdapter.ExerciseMuscleRecyclerHolder.ClickListener,
        ExerciseMuscleFavoriteRecyclerAdapter.ExerciseMuscleFavoriteRecyclerHolder.ClickListener {

    private static final String TAG = ExerciseMuscleActivity.class.getSimpleName();

    @BindView(R.id.list_muscle_exercise)
    RecyclerView listMuscleExercise;
    @BindView(R.id.favoriteEachExercise)
    ImageView favoriteEachExercise;
    @BindView(R.id.message_favorite)
    TextView messageFavorite;

    private ExerciseMuscleRecyclerAdapter mAdapter;
    private ExerciseMuscleFavoriteRecyclerAdapter mFavoriteAdapter;

    private List<ExerciseMuscleDetail> exerciseMuscleDetailList;
    private boolean isSetFavorite;
    private boolean isFetchData = false;
    private boolean isChooseFavorite = false;
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
        isChooseFavorite = false;
        favoriteEachExercise.setImageResource(R.drawable.ic_star_border_yellow_24dp);
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
                if (!isChooseFavorite) {//false -> true
                    fetchExerciseFavorite();
                } else {
                    initView();
                }
                break;

        }
    }

    private void fetchExerciseFavorite() {
        isChooseFavorite = true;
        favoriteEachExercise.setImageResource(R.drawable.ic_star_yellow_24dp);
        exerciseMuscleDetailList = new ArrayList<>();
        exerciseMuscleDetailList.clear();
        mFavoriteAdapter = new ExerciseMuscleFavoriteRecyclerAdapter(this, exerciseMuscleDetailList, this);
        listMuscleExercise.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);//hien thi 2 cot
        listMuscleExercise.setLayoutManager(gridLayoutManager);
        listMuscleExercise.setAdapter(mFavoriteAdapter);

        //when isFetchData == true
        if (exerciseMuscleDetailList.size() == 0 && isFetchData) {
            messageFavorite.setVisibility(View.VISIBLE);
        }

        //before fetch data
        if (nameExercise != null) {
            messageFavorite.setVisibility(View.GONE);
            getDataFavoriteFromFirebase();
        }

    }

    private void getDataFavoriteFromFirebase() {
        if (nameExercise.equals("Chest")) {
            reference.child(Common.FIREBASE_MUSCLE_EXERCISE_CHEST_TABLE).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ExerciseMuscleDetail muscleDetail = snapshot.getValue(ExerciseMuscleDetail.class);
                        if (muscleDetail.isFavorite()) {//favorite is true to add to list
                            exerciseMuscleDetailList.add(muscleDetail);
                            isFetchData = true;
                        }
                    }
                    //after fetch data
                    if (exerciseMuscleDetailList.size() == 0) {
                        messageFavorite.setVisibility(View.VISIBLE);
                    }
                    mFavoriteAdapter.notifyDataSetChanged();
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
                        if (muscleDetail.isFavorite()) {//favorite is true to add to list
                            exerciseMuscleDetailList.add(muscleDetail);
                            isFetchData = true;
                        }
                    }
                    //after fetch data
                    if (exerciseMuscleDetailList.size() == 0) {
                        messageFavorite.setVisibility(View.VISIBLE);
                    }
                    mFavoriteAdapter.notifyDataSetChanged();
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
                        if (muscleDetail.isFavorite()) {//favorite is true to add to list
                            exerciseMuscleDetailList.add(muscleDetail);
                            isFetchData = true;
                        }
                    }
                    //after fetch data
                    if (exerciseMuscleDetailList.size() == 0) {
                        messageFavorite.setVisibility(View.VISIBLE);
                    }
                    mFavoriteAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, "Database error: " + databaseError.getMessage());
                }
            });
        }
    }

    //click card item at list favorite
    @Override
    public void onFavoriteClickItem(int position) {
        Log.d(TAG, "position favorite item click  " + mFavoriteAdapter.getExerciseMuscleDetail().get(position));
    }

    //click favorite in card at list favorite
    @Override
    public void onFavoriteClickFavoriteItem(int position) {
        if (mFavoriteAdapter.getExerciseMuscleDetail().get(position).isFavorite()) {//favorite is true
            mFavoriteAdapter.getExerciseMuscleDetail().get(position).setFavorite(false);
            isSetFavorite = false;

            currentPosition = mFavoriteAdapter.getExerciseMuscleDetail().get(position);
            uploadSetFavorite(isSetFavorite);
            //when click dislike  exerciseMuscleDetailList remove item with position in list
            exerciseMuscleDetailList.remove(position);
            //recycler remove item in list
            listMuscleExercise.removeViewAt(position);
            //adapter is notifyItemRemoved
            mFavoriteAdapter.notifyItemRemoved(position);
            //adapter is update range of list
            mFavoriteAdapter.notifyItemRangeChanged(position, exerciseMuscleDetailList.size());

            mFavoriteAdapter.notifyDataSetChanged();

            //when unlike in list favorite
            if (exerciseMuscleDetailList.size() == 0 && isFetchData) {
                messageFavorite.setVisibility(View.VISIBLE);
            }

        } else {//favorite is false
            mFavoriteAdapter.getExerciseMuscleDetail().get(position).setFavorite(true);
            isSetFavorite = true;
            //get currentPosition to update favorite property
            currentPosition = mFavoriteAdapter.getExerciseMuscleDetail().get(position);
            uploadSetFavorite(isSetFavorite);
            mFavoriteAdapter.notifyDataSetChanged();
            Log.d(TAG, "position favorite list favorite item click " + mFavoriteAdapter.getExerciseMuscleDetail().get(position));
        }
    }
}
