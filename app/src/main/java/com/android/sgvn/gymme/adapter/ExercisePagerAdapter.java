package com.android.sgvn.gymme.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.sgvn.gymme.fragments.exerciseScheduleFragment.ExerciseListPageFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sgvn144 on 2018/04/03.
 */

public class ExercisePagerAdapter extends FragmentPagerAdapter {
    private int pageCreated;
    private String FIREBASE_SCHEDULE_DAYS;

    /**
     * Constructor
     *
     * @param fm
     */
    public ExercisePagerAdapter(FragmentManager fm, int pageCreated, String FIREBASE_SCHEDULE_DAYS) {
        super(fm);
        this.pageCreated = pageCreated;
        this.FIREBASE_SCHEDULE_DAYS = FIREBASE_SCHEDULE_DAYS;
    }

    /**
     * Return fragment based on the position
     *
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        List<ExerciseListPageFragment> listPageFragment = new ArrayList<>();
        for (int i = 0; i < pageCreated; i++) {
            listPageFragment.add(ExerciseListPageFragment.newInstance(i, FIREBASE_SCHEDULE_DAYS));
        }
        return listPageFragment.get(position);
    }

    /**
     * Return the number of pages
     *
     * @return
     */
    @Override
    public int getCount() {
        return pageCreated;
    }

}
