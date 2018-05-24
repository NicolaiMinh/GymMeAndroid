package com.android.sgvn.gymme.fragments.exerciseDetailFragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.fragments.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExerciseDetailInfoFragment extends BaseFragment {


    public ExerciseDetailInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise_detail_info, container, false);
    }

    @Override
    protected int getLayout() {
        return 0;
    }

}
