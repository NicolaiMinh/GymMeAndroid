package com.android.sgvn.gymme.fragments.tabMainFragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.activities.ExerciseMuscleActivity;
import com.android.sgvn.gymme.adapter.ExerciseMuscleListAdapter;
import com.android.sgvn.gymme.fragments.BaseFragment;
import com.android.sgvn.gymme.model.ExerciseMuscle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExerciseFragment extends BaseFragment implements ExerciseMuscleListAdapter.ItemClickListener {

    private static final String TAG = ExerciseFragment.class.getSimpleName();
    @BindView(R.id.list_muscle)
    ListView listMuscle;
    Unbinder unbinder;
    List<ExerciseMuscle> itemMuscles;
    ExerciseMuscleListAdapter mAdapter;

    public ExerciseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_exercise, container, false);
        View view = inflater.inflate(R.layout.fragment_exercisedemo, container, false);
        unbinder = ButterKnife.bind(this, view);

        itemMuscles = new ArrayList<>();
        itemMuscles.add(new ExerciseMuscle("1", "Chest"));
        itemMuscles.add(new ExerciseMuscle("2", "Shoulder"));
        itemMuscles.add(new ExerciseMuscle("3", "Arm"));
        itemMuscles.add(new ExerciseMuscle("4", "Leg"));


        mAdapter = new ExerciseMuscleListAdapter(getContext(), R.layout.custom_list_muscle_item, itemMuscles);

        listMuscle.setAdapter(mAdapter);
        mAdapter.setmItemClickListener(this);
        return view;
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

    //onclick from ExerciseMuscleListAdapter
    @Override
    public void onClick(int position) {
        if (mAdapter != null) {
            Intent intent = new Intent(getActivity(), ExerciseMuscleActivity.class);

            intent.putExtra("idExercise", itemMuscles.get(position).getId());
            intent.putExtra("nameExercise", itemMuscles.get(position).getExerciseMuscleName());

            startActivity(intent);
        }
    }
}
