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
import com.android.sgvn.gymme.common.Common;
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

    private int pageCreate;
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

    @OnClick({R.id.beginner3day, R.id.beginner4day})
    public void onCardClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.beginner3day:
                intent = new Intent(getActivity(), BeginnerActivity.class);
                pageCreate = 3;//create 3 pages- 3 days
                intent.putExtra(Common.WORKOUT_SET_PAGE_CREATED, pageCreate);
                intent.putExtra(Common.FIREBASE_SCHEDULE_DAYS, "3Days");
                startActivity(intent);
                break;
            case R.id.beginner4day:
                intent = new Intent(getActivity(), BeginnerActivity.class);
                pageCreate = 4;//create 4 pages- 4 days
                intent.putExtra(Common.WORKOUT_SET_PAGE_CREATED, pageCreate);
                intent.putExtra(Common.FIREBASE_SCHEDULE_DAYS, "4Days");
                startActivity(intent);
                break;

        }
    }
}
