package com.android.sgvn.gymme.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.sgvn.gymme.fragments.exerciseScheduleFragment.ExerciseListPageFragment;
import com.android.sgvn.gymme.fragments.tutorialFragments.FirstPageFragment;
import com.android.sgvn.gymme.fragments.tutorialFragments.SecondPageFragment;
import com.android.sgvn.gymme.fragments.tutorialFragments.ThirdPageFragment;
import com.android.sgvn.gymme.model.ExerciseMuscleDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sgvn144 on 2018/04/03.
 */

public class ExercisePagerAdapter extends FragmentPagerAdapter {
    private int pageCreated;
    private List<String> dayPresent;
    private List<ExerciseMuscleDetail> exerciseMuscleDetailList;

    /**
     * Constructor
     *
     * @param fm
     */
    public ExercisePagerAdapter(FragmentManager fm, int pageCreated, List<ExerciseMuscleDetail> exerciseMuscleDetailList, List<String> dayPresent) {
        super(fm);
        this.pageCreated = pageCreated;
        this.exerciseMuscleDetailList = exerciseMuscleDetailList;
        this.dayPresent = dayPresent;
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
        String dayPresentFetch = "";
        if (position == 0) {
            dayPresentFetch = "day1";
        } else if (position == 1) {
            dayPresentFetch = "day2";
        } else if (position == 2) {
            dayPresentFetch = "day3";
        } else if (position == 3) {
            dayPresentFetch = "day4";
        }

        List<ExerciseMuscleDetail> muscleDetailList = groupListByDayPresent(dayPresentFetch);
        for (int i = 0; i < pageCreated; i++) {
            listPageFragment.add(ExerciseListPageFragment.newInstance(i, muscleDetailList, dayPresent));
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

    private List<ExerciseMuscleDetail> groupListByDayPresent(final String dayPresent) {
        List<ExerciseMuscleDetail> muscleDetailList = new ArrayList<>();
            for (ExerciseMuscleDetail person : muscleDetailList) {
            if (person.getDayPresent().equals(dayPresent)) {
                muscleDetailList.add(person);
            }
        }
//        for (int i = 0; i < exerciseMuscleDetailList.size(); i++) {
//            if (exerciseMuscleDetailList.get(i).getDayPresent().equals(dayPresent)) {
//
//            }
//        }

        return muscleDetailList;
    }
}
