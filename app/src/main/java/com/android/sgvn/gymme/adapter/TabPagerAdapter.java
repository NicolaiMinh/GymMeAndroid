package com.android.sgvn.gymme.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.sgvn.gymme.fragments.tabMainFragments.ExerciseFragment;
import com.android.sgvn.gymme.fragments.tabMainFragments.MealPlanFragment;
import com.android.sgvn.gymme.fragments.tabMainFragments.MoreFragment;
import com.android.sgvn.gymme.fragments.tabMainFragments.ProgressFragment;
import com.android.sgvn.gymme.fragments.tabMainFragments.WorkoutFragment;

/**
 * Created by sgvn144 on 2018/04/03.
 */

public class TabPagerAdapter extends FragmentPagerAdapter {

    int tabCount;//so luong tab


    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    /**
     * Show fragment when focus position
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        //return a current tab
        switch (position) {
            case 0:
                ExerciseFragment exerciseFragment = new ExerciseFragment();
                return exerciseFragment;
            case 1:
                WorkoutFragment workoutFragment = new WorkoutFragment();
                return  workoutFragment;
            case 2:
                ProgressFragment progressFragment = new ProgressFragment();
                return  progressFragment;
            case 3:
                MealPlanFragment mealPlanFragment = new MealPlanFragment();
                return  mealPlanFragment;
            case 4:
                MoreFragment moreFragment = new MoreFragment();
                return  moreFragment;
            default:
                return null;
        }
    }

    /**
     * Return tabCount
     * @return
     */
    @Override
    public int getCount() {
        return tabCount;
    }
}
