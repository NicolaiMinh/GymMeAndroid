package com.android.sgvn.gymme.activities.exerciseschedule;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.activities.BaseActivity;
import com.android.sgvn.gymme.adapter.ExercisePagerAdapter;
import com.android.sgvn.gymme.common.Common;
import com.android.sgvn.gymme.model.ExerciseMuscleDetail;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    private int pageCreate;
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

        setupViewPager();

    }

        private void setupWorkoutText() {
        if (pageCreate == 3) {
            workoutText.setText(R.string.workout_3_days);
        } else if (pageCreate == 4) {
            workoutText.setText(R.string.workout_4_days);
        }
    }


    private void setupViewPager() {
        //setup viewpager present layout though ExercisePagerAdapter
        String FIREBASE_SCHEDULE_DAYS = getIntent().getStringExtra(Common.FIREBASE_SCHEDULE_DAYS);
        viewPager.setAdapter(new ExercisePagerAdapter(getSupportFragmentManager(), pageCreate, FIREBASE_SCHEDULE_DAYS));
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
