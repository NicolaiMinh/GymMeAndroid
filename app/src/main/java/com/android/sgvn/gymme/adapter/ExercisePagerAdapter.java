package com.android.sgvn.gymme.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.sgvn.gymme.fragments.exerciseScheduleFragment.ExerciseListPageFragment;
import com.android.sgvn.gymme.fragments.tutorialFragments.FirstPageFragment;
import com.android.sgvn.gymme.fragments.tutorialFragments.SecondPageFragment;
import com.android.sgvn.gymme.fragments.tutorialFragments.ThirdPageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sgvn144 on 2018/04/03.
 */

public class ExercisePagerAdapter extends FragmentPagerAdapter {
    private int pageCreated;

    /**
     * Constructor
     *
     * @param fm
     */
    public ExercisePagerAdapter(FragmentManager fm, int pageCreated) {
        super(fm);
        this.pageCreated = pageCreated;
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
            listPageFragment.add(ExerciseListPageFragment.newInstance(i, "ExerciseListPageFragment, Instance " + i));
        }
        return listPageFragment.get(position);

//        switch (position) {
//            case 0:
//                return ExerciseListPageFragment.newInstance(0, "ExerciseListPageFragment, Instance 1");
//            case 1:
//                return ExerciseListPageFragment.newInstance(1, "ExerciseListPageFragment, Instance 2");
//            case 2:
//                return ExerciseListPageFragment.newInstance(2, "ExerciseListPageFragment, Instance 3");
//            default:
//                return null;
//        }
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
