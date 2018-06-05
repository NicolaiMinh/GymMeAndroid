package com.android.sgvn.gymme.fragments.exerciseScheduleFragment;


import android.app.Fragment;
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
import com.android.sgvn.gymme.adapter.WorkoutRecyclerAdapter;
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
    private List<ExerciseMuscleDetail> exerciseMuscleDetailListWork;
    private WorkoutRecyclerAdapter mAdapter;

    public void addData(List<ExerciseMuscleDetail> exerciseMuscleDetailList) {
        exerciseMuscleDetailListWork = new ArrayList<>();
        this.exerciseMuscleDetailList = exerciseMuscleDetailList;
        exerciseMuscleDetailListWork.addAll(exerciseMuscleDetailList);
    }

    /**
     * Returns new instance.
     *
     * @param text
     * @return
     */
    public static ExerciseListPageFragment newInstance(int page, String text) {

        // new instance
        ExerciseListPageFragment instance = new ExerciseListPageFragment();

        // sets data to bundle
        Bundle bundle = new Bundle();
        bundle.putString("msg", text);
        bundle.putInt("page", page);

        // set data to fragment
        instance.setArguments(bundle);

        return instance;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String msg = getArguments().getString("msg");
        int page = getArguments().getInt("page");

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
        mAdapter = new WorkoutRecyclerAdapter(getContext(), exerciseMuscleDetailListWork, this);
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
    }
}
