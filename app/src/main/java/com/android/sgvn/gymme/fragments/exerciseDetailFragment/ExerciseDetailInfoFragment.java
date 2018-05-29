package com.android.sgvn.gymme.fragments.exerciseDetailFragment;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.activities.ExerciseMuscleActivity;
import com.android.sgvn.gymme.activities.ExerciseMuscleDetailActivity;
import com.android.sgvn.gymme.common.Common;
import com.android.sgvn.gymme.fragments.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExerciseDetailInfoFragment extends BaseFragment {
    private static final String TAG = ExerciseDetailInfoFragment.class.getSimpleName();
    @BindView(R.id.text)
    TextView text;
    Unbinder unbinder;

    public ExerciseDetailInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null)
        {
            String s = bundle.getString("params");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise_detail_info, container, false);
        unbinder = ButterKnife.bind(this, view);

        String data = savedInstanceState.getString("params");

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


}
