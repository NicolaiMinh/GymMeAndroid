package com.android.sgvn.gymme.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.sgvn.gymme.fragments.tutorialFragments.FirstPageFragment;
import com.android.sgvn.gymme.fragments.tutorialFragments.SecondPageFragment;
import com.android.sgvn.gymme.fragments.tutorialFragments.ThirdPageFragment;

/**
 * Created by sgvn144 on 2018/04/03.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    private static final int NUMBER_OF_PAGES = 3;


    /**
     * Constructor
     * @param fm
     */
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * Return fragment based on the position
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return FirstPageFragment.newInstance("FirstFragment, Instance 1");
            case 1: return SecondPageFragment.newInstance("SecondPageFragment, Instance 1");
            case 2: return ThirdPageFragment.newInstance("ThirdPageFragment, Instance 1");
            default: return FirstPageFragment.newInstance("FirstFragment, Default");
        }
    }

    /**
     * Return the number of pages
     * @return
     */
    @Override
    public int getCount() {
        return NUMBER_OF_PAGES;
    }

}
