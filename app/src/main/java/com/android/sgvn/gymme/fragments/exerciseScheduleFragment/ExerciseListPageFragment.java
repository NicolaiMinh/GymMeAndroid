package com.android.sgvn.gymme.fragments.exerciseScheduleFragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.fragments.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExerciseListPageFragment extends BaseFragment {


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
        return inflater.inflate(R.layout.fragment_exercise_list_page, container, false);
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


}
