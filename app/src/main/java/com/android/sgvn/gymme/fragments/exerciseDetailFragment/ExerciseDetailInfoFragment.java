package com.android.sgvn.gymme.fragments.exerciseDetailFragment;


import android.app.Fragment;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.fragments.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExerciseDetailInfoFragment extends BaseFragment {
    private static final String TAG = ExerciseDetailInfoFragment.class.getSimpleName();
    List<String> receiveData;
    Unbinder unbinder;
    @BindView(R.id.primary_muscle)
    TextView primaryMuscle;
    @BindView(R.id.secondary_muscle)
    TextView secondaryMuscle;
    @BindView(R.id.preparation)
    TextView preparation;
    @BindView(R.id.execution)
    TextView execution;
    @BindView(R.id.layout_secondary_muscle)
    LinearLayout layoutSecondaryMuscle;

    String primaryMuscleReceive, secondaryMuscleReceive, preparationReceive, executionReceive;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //add Data from adapter
    public void addData(List<String> receiveData) {
        this.receiveData = receiveData;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise_detail_info, container, false);
        unbinder = ButterKnife.bind(this, view);

        primaryMuscleReceive = receiveData.get(0);//Common.EXERCISE_DETAIL_PRIMARY_MUSCLE
        secondaryMuscleReceive = receiveData.get(1);//Common.EXERCISE_DETAIL_SECONDARY_MUSCLE
        preparationReceive = receiveData.get(2);//Common.EXERCISE_DETAIL_PREPARATION
        executionReceive = receiveData.get(3);//Common.EXERCISE_DETAIL_EXECUTION

        Log.d(TAG + " onCreateView ", primaryMuscleReceive + " " + secondaryMuscleReceive + " " + preparationReceive + " " + executionReceive);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view != null) {
            primaryMuscle.setText(primaryMuscleReceive);
            if (secondaryMuscleReceive.isEmpty()) {//secondary = empty
                layoutSecondaryMuscle.setVisibility(View.INVISIBLE);
            } else {
                secondaryMuscle.setText(secondaryMuscleReceive);
            }
            preparation.setText(preparationReceive);
            execution.setText(executionReceive);
        }
    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
