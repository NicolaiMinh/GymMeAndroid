package com.android.sgvn.gymme.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.sgvn.gymme.fragments.exerciseDetailFragment.ExerciseDetailImageFragment;
import com.android.sgvn.gymme.fragments.exerciseDetailFragment.ExerciseDetailInfoFragment;
import com.android.sgvn.gymme.fragments.exerciseDetailFragment.ExerciseDetailVideoFragment;

/**
 * Created by sgvn144 on 2018/04/03.
 */

public class TabPagerExerciseDetailAdapter extends FragmentPagerAdapter {

    int tabCount;//so luong tab
    String data;


    public TabPagerExerciseDetailAdapter(FragmentManager fm, int tabCount, String data) {
        super(fm);
        this.tabCount = tabCount;
        this.data = data;
    }

    /**
     * Show fragment when focus position
     *
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        //return a current tab
        switch (position) {
            case 0:
                ExerciseDetailImageFragment exerciseDetailImageFragment = new ExerciseDetailImageFragment();
                exerciseDetailImageFragment. addData(data);
                return exerciseDetailImageFragment;
            case 1:
                ExerciseDetailVideoFragment exerciseDetailVideoFragment = new ExerciseDetailVideoFragment();
                return exerciseDetailVideoFragment;
            case 2:
                ExerciseDetailInfoFragment exerciseDetailInfoFragment = new ExerciseDetailInfoFragment();
                exerciseDetailInfoFragment.addData(data);
                return exerciseDetailInfoFragment;
            default:
                return null;
        }
    }

    /**
     * Return tabCount
     *
     * @return
     */
    @Override
    public int getCount() {
        return tabCount;
    }
}
