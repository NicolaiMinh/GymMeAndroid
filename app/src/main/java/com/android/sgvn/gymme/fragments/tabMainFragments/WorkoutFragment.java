package com.android.sgvn.gymme.fragments.tabMainFragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.activities.exerciseschedule.AdvanceActivity;
import com.android.sgvn.gymme.activities.exerciseschedule.BeginnerActivity;
import com.android.sgvn.gymme.fragments.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkoutFragment extends BaseFragment {


    @BindView(R.id.beginner3day)
    CardView beginner3day;
    @BindView(R.id.beginner4day)
    CardView beginner4day;
//    @BindView(R.id.advance)
//    CardView advance;
    Unbinder unbinder;

    public WorkoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_workout, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

    @OnClick({R.id.beginner3day,R.id.beginner4day})
    public void onCardClick(View view) {
        switch (view.getId()) {
            case R.id.beginner3day:
                startActivity(new Intent(getActivity(), BeginnerActivity.class));
                break;
            case R.id.beginner4day:
                startActivity(new Intent(getActivity(), BeginnerActivity.class));
                break;

        }
    }
}
