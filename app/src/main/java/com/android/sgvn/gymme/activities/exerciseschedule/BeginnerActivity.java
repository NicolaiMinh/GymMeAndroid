package com.android.sgvn.gymme.activities.exerciseschedule;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.activities.BaseActivity;
import com.android.sgvn.gymme.adapter.ExercisePagerAdapter;
import com.android.sgvn.gymme.common.Common;
import com.android.sgvn.gymme.model.ExerciseMuscleDetail;
import com.android.sgvn.gymme.model.ExerciseSchedule;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BeginnerActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = BeginnerActivity.class.getSimpleName();

    @BindView(R.id.imb_back_toolbar)
    ImageButton imbBackToolbar;
    @BindView(R.id.text)
    LinearLayout text;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.viewPagerCountDots)
    LinearLayout viewPagerCountDots;
    @BindView(R.id.workout_text)
    TextView workoutText;

    int pageCreate;
    private int muscleID;
    private String muscleExercise;
    private boolean fetchDataFinish = false;

    private int dotsCount;//dau cham dot tuong ung moi slider
    private ImageView[] dots;// imgae dot dc hien thi khi focus

    private List<ExerciseMuscleDetail> exerciseMuscleDetailList;

    //firebase
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginner);
        ButterKnife.bind(this);

        //init firebase
        database = FirebaseDatabase.getInstance();
        reference = database.getReference(Common.FIREBASE_SCHEDULE_TABLE);

        //get page from WorkoutFragment
        pageCreate = getIntent().getIntExtra(Common.WORKOUT_SET_PAGE_CREATED, -1);
        setupWorkoutText();

        //getDataScheduleFromFireBase
        queryExerciseByMuscleID();

        //setup viewPager
        setupViewPager();
    }

    private void queryExerciseByMuscleID() {
        exerciseMuscleDetailList = new ArrayList<>();
        String FIREBASE_SCHEDULE_DAYS = getIntent().getStringExtra(Common.FIREBASE_SCHEDULE_DAYS);

        if (FIREBASE_SCHEDULE_DAYS != null && !FIREBASE_SCHEDULE_DAYS.isEmpty()) {
            //get data from firebase
            reference.child(Common.FIREBASE_SCHEDULE_BEGINNER).child(FIREBASE_SCHEDULE_DAYS).child(Common.FIREBASE_SCHEDULE_EXERCISE).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ExerciseSchedule exerciseSchedule = snapshot.getValue(ExerciseSchedule.class);
                        muscleID = exerciseSchedule.getMuscleID();
                        muscleExercise = exerciseSchedule.getMuscleExercise();
                        String dayPresent = exerciseSchedule.getDayPresent();

                        reference = database.getReference(Common.FIREBASE_MUSCLE_EXERCISE_TABLE);

                        //query data from FIREBASE_MUSCLE_EXERCISE_TABLE by muscleID
                        Query query = reference.child(muscleExercise).orderByChild("id").equalTo(muscleID);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        ExerciseMuscleDetail muscleDetail = snapshot.getValue(ExerciseMuscleDetail.class);
                                        exerciseMuscleDetailList.add(muscleDetail);
                                    }
//                                    setupViewPager();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.d(TAG, "query error: " + databaseError.getMessage());
                            }
                        });

                    }
                    Log.d(TAG, "muscleID: " + muscleID);
                    Log.d(TAG, "muscleExercise: " + muscleExercise);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, "Database error: " + databaseError.getMessage());
                }
            });
        }
    }

    private void setupWorkoutText() {
        if (pageCreate == 3) {
            workoutText.setText(R.string.workout_3_days);
        } else if (pageCreate == 4) {
            workoutText.setText(R.string.workout_4_days);
        }
    }


    private void setupViewPager() {
//        queryExerciseByMuscleID();

        //setup viewpager present layout though MyPagerAdapter
        viewPager.setAdapter(new ExercisePagerAdapter(getSupportFragmentManager(), pageCreate, exerciseMuscleDetailList));
        viewPager.addOnPageChangeListener(this);

        //setup layout dot
        setupUIPageViewController();
    }

    private void setupUIPageViewController() {
        //khoi tao dotsCount
        dotsCount = pageCreate;
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.nonselecteditem_dot_yellow));

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.setMargins(5, 0, 5, 0);
            viewPagerCountDots.addView(dots[i], layoutParams);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.selecteditem_dot_yellow));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.nonselecteditem_dot_yellow));
            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == dotsCount - 1) {
//                // last page. make button text to GOT IT
            } else {
                // still pages are left
            }
        }
        dots[position].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.selecteditem_dot_yellow));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick({R.id.imb_back_toolbar})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.imb_back_toolbar:
                finish();
                break;
        }
    }

   }
