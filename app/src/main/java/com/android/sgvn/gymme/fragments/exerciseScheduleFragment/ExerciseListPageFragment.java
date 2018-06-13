package com.android.sgvn.gymme.fragments.exerciseScheduleFragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.activities.ExerciseMuscleDetailActivity;
import com.android.sgvn.gymme.adapter.WorkoutRecyclerAdapter;
import com.android.sgvn.gymme.common.Common;
import com.android.sgvn.gymme.fragments.BaseFragment;
import com.android.sgvn.gymme.model.ExerciseMuscleDetail;
import com.android.sgvn.gymme.model.ExerciseSchedule;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExerciseListPageFragment extends BaseFragment implements WorkoutRecyclerAdapter.WorkoutRecyclerHolder.ClickListener {

    private static final String TAG = ExerciseListPageFragment.class.getSimpleName();
    @BindView(R.id.recycler_view_exercise)
    RecyclerView recyclerViewExercise;
    Unbinder unbinder;

    private List<ExerciseMuscleDetail> exerciseMuscleDetailList;
    private WorkoutRecyclerAdapter mAdapter;

    private int position;
    private int muscleID;
    private String muscleExercise;

    //firebase
    private FirebaseDatabase database;
    private DatabaseReference reference;

    private String receiveData;
    private String dayPresent;

    public static ExerciseListPageFragment newInstance(int position, String receiveData) {
        // new instance
        ExerciseListPageFragment instance = new ExerciseListPageFragment();
        // sets data to bundle
        Bundle bundle = new Bundle();

        bundle.putInt("position", position);
        bundle.putString("receiveData", receiveData);
        // set data to fragment
        instance.setArguments(bundle);

        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //init firebase
        database = FirebaseDatabase.getInstance();
        reference = database.getReference(Common.FIREBASE_SCHEDULE_TABLE);

        position = getArguments() != null ? getArguments().getInt("position") : 0;
        receiveData = getArguments() != null ? getArguments().getString("receiveData") : "";
        dayPresent = convertPositionToDayPresent(position);

        Log.d(TAG +" onCreate ", String.valueOf(position) + " "+receiveData);
    }

    private String convertPositionToDayPresent(int position) {
        String day = "";
        switch (position){
            case 0:  day = "Day1"; break;
            case 1:  day = "Day2"; break;
            case 2:  day = "Day3"; break;
            case 3:  day = "Day4"; break;
            case 4:  day = "Day5"; break;
            default: day = "Day1"; break;
        }
        return day;
    }

    /**
     * Create fragment view when paginated.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise_list_page, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        exerciseMuscleDetailList = new ArrayList<>();
        mAdapter = new WorkoutRecyclerAdapter(getContext(), exerciseMuscleDetailList, this);
        recyclerViewExercise.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewExercise.setLayoutManager(linearLayoutManager);
        recyclerViewExercise.setAdapter(mAdapter);
        queryExerciseByMuscleID();
    }

    /**
     * Return the position of page
     *
     * @return
     */
    @Override
    protected int getLayout() {
        return 0;
    }


    private void queryExerciseByMuscleID() {
        if (receiveData != null && !receiveData.isEmpty()) {
            //get data from firebase
            reference.child(Common.FIREBASE_SCHEDULE_BEGINNER).child(receiveData).child(Common.FIREBASE_SCHEDULE_EXERCISE).child(dayPresent).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        final ExerciseSchedule exerciseSchedule = snapshot.getValue(ExerciseSchedule.class);
                        muscleID = exerciseSchedule.getMuscleID();
                        muscleExercise = exerciseSchedule.getMuscleExercise();

                        final int count = (int) dataSnapshot.getChildrenCount();

                        reference = database.getReference(Common.FIREBASE_MUSCLE_EXERCISE_TABLE);

                        //query data from FIREBASE_MUSCLE_EXERCISE_TABLE by muscleID
                        Query query = reference.child(muscleExercise).orderByChild("id").equalTo(muscleID);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        ExerciseMuscleDetail muscleDetail = snapshot.getValue(ExerciseMuscleDetail.class);
                                        exerciseMuscleDetailList.add(muscleDetail);
                                    }
                                    mAdapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.d(TAG, "query error: " + databaseError.getMessage());
                            }
                        });

                    }
                    Log.d(TAG, "muscleID: " + muscleID);
                    Log.d(TAG, "muscleExercise: " + muscleExercise);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, "Database error: " + databaseError.getMessage());
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //implement click on each item
    @Override
    public void onClickItem(int position) {
        Log.d(TAG, "position " + mAdapter.getExerciseMuscleDetail().get(position));
        if (mAdapter != null) {
            Intent intent = new Intent(getActivity(), ExerciseMuscleDetailActivity.class);
            intent.putExtra(Common.EXERCISE_DETAIL_NAME, mAdapter.getExerciseMuscleDetail().get(position).getExerciseName());
            intent.putExtra(Common.EXERCISE_DETAIL_FAVORITE, mAdapter.getExerciseMuscleDetail().get(position).isFavorite());
            intent.putExtra(Common.EXERCISE_DETAIL_IMAGE, mAdapter.getExerciseMuscleDetail().get(position).getImageURL());
            intent.putExtra(Common.EXERCISE_DETAIL_VIDEO_URL, mAdapter.getExerciseMuscleDetail().get(position).getVideoURL());
            intent.putExtra(Common.EXERCISE_DETAIL_EXECUTION, mAdapter.getExerciseMuscleDetail().get(position).getExecution());
            intent.putExtra(Common.EXERCISE_DETAIL_PREPARATION, mAdapter.getExerciseMuscleDetail().get(position).getPreparation());
            intent.putExtra(Common.EXERCISE_DETAIL_PRIMARY_MUSCLE, mAdapter.getExerciseMuscleDetail().get(position).getPrimaryMuscle());
            intent.putExtra(Common.EXERCISE_DETAIL_SECONDARY_MUSCLE, mAdapter.getExerciseMuscleDetail().get(position).getSecondaryMucsle());
            startActivity(intent);
        }
    }
}
