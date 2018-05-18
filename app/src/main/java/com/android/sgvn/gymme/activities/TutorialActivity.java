package com.android.sgvn.gymme.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.adapter.MyPagerAdapter;
import com.android.sgvn.gymme.manager.PrefManager;
import com.android.sgvn.gymme.utils.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TutorialActivity extends BaseLoginActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.viewPagerCountDots)
    LinearLayout mViewPagerCountDots;
    @BindView(R.id.btnSkip)
    Button btnSkip;
    @BindView(R.id.btnNext)
    Button btnNext;

    private int dotsCount;//dau cham dot tuong ung moi slider
    private ImageView[] dots;// imgae dot dc hien thi khi focus

    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        ButterKnife.bind(this);

        //setup viewPager
        setupViewPager();

        // Checking for first time launch - before calling setContentView()
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        Intent intent = new Intent(TutorialActivity.this, MainActivity.class);
        intent.putExtra(AppConstants.START_SPLASH, true);
        finish();
    }

    private void setupViewPager() {
        //setup viewpager present layout though MyPagerAdapter
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(this);

        //setup layout dot
        setupUIPageViewController();
    }

    private void setupUIPageViewController() {
        //khoi tao 3 dots
        dotsCount = 3;
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.setMargins(80, 0, 80, 0);
            mViewPagerCountDots.addView(dots[i], layoutParams);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.selecteditem_dot));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.nonselecteditem_dot));
            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == dotsCount - 1) {
                // last page. make button text to GOT IT
                btnNext.setText("Got it");
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setText("Next");
                btnSkip.setVisibility(View.VISIBLE);
            }
        }
        dots[position].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.selecteditem_dot));

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //onclick btn Skip and Next
    @OnClick({R.id.btnSkip, R.id.btnNext})
    public void buttonOnClick(View view){
        switch (view.getId()){
            case R.id.btnNext:
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < dots.length) {
                    // move to next screen
                    mViewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
                break;
            case R.id.btnSkip:
                launchHomeScreen();
                break;

        }
    }

    private int getItem(int i) {
        return mViewPager.getCurrentItem() + i;
    }
}
