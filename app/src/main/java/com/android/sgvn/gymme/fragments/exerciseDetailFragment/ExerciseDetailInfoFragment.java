package com.android.sgvn.gymme.fragments.exerciseDetailFragment;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.common.Common;
import com.android.sgvn.gymme.fragments.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExerciseDetailInfoFragment extends BaseFragment {
    private static final String TAG = ExerciseDetailInfoFragment.class.getSimpleName();

    public ExerciseDetailInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getArguments() != null) {
            String strtext = this.getArguments().getString(Common.EXERCISE_DETAIL_EXECUTION);
            String strtext1 = this.getArguments().getString(Common.EXERCISE_DETAIL_PREPARATION);
            String strtext2 = this.getArguments().getString(Common.EXERCISE_DETAIL_PRIMARY_MUSCLE);
            String strtext3 = this.getArguments().getString(Common.EXERCISE_DETAIL_SECONDARY_MUSCLE);

            Log.d(TAG, strtext + strtext1 + strtext2 + strtext3);
        }

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
