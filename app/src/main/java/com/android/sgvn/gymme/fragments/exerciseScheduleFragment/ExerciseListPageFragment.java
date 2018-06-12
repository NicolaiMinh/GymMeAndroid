package com.android.sgvn.gymme.fragments.exerciseScheduleFragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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

    ArrayList<ExerciseMuscleDetail> myList;
    int position;
    ArrayList<String> dayPresent;

    /**
     * @param position
     * @param exerciseMuscleDetailList
     * @return
     */
    public static ExerciseListPageFragment newInstance(int position, List<ExerciseMuscleDetail> exerciseMuscleDetailList, List<String> dayPresent) {

        // new instance
        ExerciseListPageFragment instance = new ExerciseListPageFragment();
        ArrayList<ExerciseMuscleDetail> arraylist = new ArrayList<ExerciseMuscleDetail>();
        arraylist.addAll(exerciseMuscleDetailList);

        ArrayList<String> arraylistString = new ArrayList<String>();
        arraylistString.addAll(dayPresent);
        // sets data to bundle
        Bundle bundle = new Bundle();

        bundle.putInt("position", position);
        bundle.putStringArrayList("dayPresent", arraylistString);
        bundle.putParcelableArrayList("exerciseMuscleDetailList", arraylist);

        // set data to fragment
        instance.setArguments(bundle);

        return instance;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments() != null ? getArguments().getInt("position") : 0;
        dayPresent = getArguments().getStringArrayList("dayPresent");
        myList = getArguments().getParcelableArrayList("exerciseMuscleDetailList");
        Log.d(TAG, myList.toString());
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
        exerciseMuscleDetailList.addAll(myList);
        mAdapter = new WorkoutRecyclerAdapter(getContext(), exerciseMuscleDetailList, this);
        recyclerViewExercise.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewExercise.setLayoutManager(linearLayoutManager);
        recyclerViewExercise.setAdapter(mAdapter);
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
