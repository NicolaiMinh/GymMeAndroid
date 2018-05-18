package com.android.sgvn.gymme.fragments.tutorialFragments;


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
public class ThirdPageFragment extends BaseFragment {
    private static ThirdPageFragment instance = null;

    /**
     * Returns new instance.
     *
     * @param text
     * @return
     */
    public static ThirdPageFragment newInstance(String text) {

        if (instance == null) {
            // new instance
            instance = new ThirdPageFragment();

            // sets data to bundle
            Bundle bundle = new Bundle();
            bundle.putString("msg", text);

            // set data to fragment
            instance.setArguments(bundle);

            return instance;
        } else {

            return instance;
        }

    }

    /**
     * Create fragment view when paginated.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third_page, container, false);
    }

    @Override
    protected int getLayout() {
        return 0;
    }

}
