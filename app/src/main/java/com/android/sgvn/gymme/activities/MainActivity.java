package com.android.sgvn.gymme.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.adapter.TabPagerAdapter;
import com.android.sgvn.gymme.utils.AppConstants;
import com.crashlytics.android.Crashlytics;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends BaseLoginActivity implements TabLayout.OnTabSelectedListener {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;

    private Unbinder unbinder;
    private TabPagerAdapter mAdapter;


    public boolean isOpenSplash = false;
    public int indexPager = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set up Butterknife
        unbinder = ButterKnife.bind(this);

        //set up Fabric
        Fabric.with(MainActivity.this, new Crashlytics());


        //init a openSplash
        isOpenSplash = getIntent().getBooleanExtra(AppConstants.START_SPLASH, false);

//        start Splash
        openSplash();

        //set up TabLayout
        setupTabLayout();

    }

    private void openSplash() {
        if(!isOpenSplash){//return false
            isOpenSplash = !isOpenSplash;// set true
            startActivity(new Intent(MainActivity.this, SplashActivity.class));
        }
    }

    private void setupTabLayout() {
        //add tab
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(getTabView(getString(R.string.tab_exercise), R.drawable.ic_dumbell1)));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(getTabView(getString(R.string.tab_workout), R.drawable.ic_view_list_black_24dp)));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(getTabView(getString(R.string.tab_progress), R.drawable.ic_schedule_black_24dp)));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(getTabView(getString(R.string.tab_meal), R.drawable.ic_food_outline)));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(getTabView(getString(R.string.tab_more), R.drawable.ic_more_horiz_black_24dp)));

        //set up layout
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Creating adapter to pager
        mAdapter = new TabPagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());

        //Adding adapter to view pager
        mViewpager.setAdapter(mAdapter);

        //Adding onTabSelectedListener to swipe views
        mTabLayout.setOnTabSelectedListener(this);

        selectPage(indexPager);

    }

    private void selectPage(int pageIndex) {
        TabLayout.Tab tab = mTabLayout.getTabAt(pageIndex);
        tab.select();
    }


    public View getTabView(String titleName, int imageResource) {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_main_tab_layout, null);
        final TextView title = view.findViewById(R.id.title);//title in custom_main_tab_layout
        final ImageView imageView = view.findViewById(R.id.img_tab);//img_tab in custom_main_tab_layout
        title.setText(titleName);
        imageView.setImageResource(imageResource);

        view.setPadding(0, 0, 0, 0);
        return view;

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewpager.setCurrentItem(tab.getPosition(), false);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
