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
public class FirstPageFragment extends BaseFragment {

    private static FirstPageFragment instance = null;

    /**
     * Returns new instance.
     *
     * @param text
     * @return
     */
    public static FirstPageFragment newInstance(String text) {

        if (instance == null) {
            // new instance
            instance = new FirstPageFragment();

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
        return inflater.inflate(R.layout.fragment_first_page, container, false);
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
