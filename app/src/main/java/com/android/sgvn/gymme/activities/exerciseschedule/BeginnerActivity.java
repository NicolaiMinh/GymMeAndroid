package com.android.sgvn.gymme.activities.exerciseschedule;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.adapter.ExercisePagerAdapter;
import com.android.sgvn.gymme.adapter.MyPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BeginnerActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.imb_back_toolbar)
    ImageButton imbBackToolbar;
    @BindView(R.id.text)
    LinearLayout text;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.viewPagerCountDots)
    LinearLayout viewPagerCountDots;

    int pageCreate = 3;

    private int dotsCount;//dau cham dot tuong ung moi slider
    private ImageView[] dots;// imgae dot dc hien thi khi focus

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginner);
        ButterKnife.bind(this);

        //setup viewPager
        setupViewPager();
    }

    private void setupViewPager() {
        //setup viewpager present layout though MyPagerAdapter
        viewPager.setAdapter(new ExercisePagerAdapter(getSupportFragmentManager(),pageCreate));
        viewPager.addOnPageChangeListener(this);

        //setup layout dot
        setupUIPageViewController();
    }

    private void setupUIPageViewController() {
        //khoi tao 3 dots
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
}
