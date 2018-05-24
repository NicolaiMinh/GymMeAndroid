package com.android.sgvn.gymme.activities;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.adapter.TabPagerExerciseDetailAdapter;
import com.android.sgvn.gymme.common.Common;
import com.android.sgvn.gymme.common.IClickFavoriteItem;
import com.android.sgvn.gymme.customview.NonSwipeableViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ExerciseMuscleDetailActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private static final String TAG = ExerciseMuscleDetailActivity.class.getSimpleName();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.shareExercise)
    ImageView shareExercise;
    @BindView(R.id.exerciseName)
    TextView exerciseName;
    @BindView(R.id.favoriteItem)
    ImageView favoriteItem;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    NonSwipeableViewPager viewpager;

    private TabPagerExerciseDetailAdapter mPagerAdapter;
    public int defaultIndexPager = 0;//ExerciseDetailImageFragment
    private Unbinder unbinder;

    IClickFavoriteItem iClickFavoriteItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_muscle_detail);
        //set up Butterknife
        unbinder = ButterKnife.bind(this);

        //setup toolbar
        setupToolbar();
        //set up TabLayout
        setupTabLayout();

        //initview
        initView();
    }

    private void initView() {
        boolean isFavorite = getIntent().getBooleanExtra(Common.EXERCISE_DETAIL_FAVORITE, false);
        String exerciseNameReceive = getIntent().getStringExtra(Common.EXERCISE_DETAIL_NAME);
        exerciseName.setText(exerciseNameReceive);
        if (isFavorite) {
            favoriteItem.setImageResource(R.drawable.ic_star_black_favorite);
        } else {
            favoriteItem.setImageResource(R.drawable.ic_star_black_item);
        }

        Log.d(TAG+" favorite + exerciseName", String.valueOf(isFavorite) + "; " + exerciseNameReceive.toString());

    }


    @OnClick({R.id.favoriteItem})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.favoriteItem:
                break;
        }
    }


    private void setupToolbar() {
        setSupportActionBar(toolbar);
        //set title again
        getSupportActionBar().setTitle("");
        //get back arrow button from toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //setup back arrow with another image or color
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.arrowBackButton), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }


    //onclick back arrow in toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //nut home la co san trong he thong
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupTabLayout() {
        //add tab
        tabLayout.addTab(tabLayout.newTab().setCustomView(getTabView(R.drawable.ic_image_black_24dp)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(getTabView(R.drawable.ic_slideshow_black_24dp)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(getTabView(R.drawable.ic_more_horiz_black_24dp)));
        //set up layout
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Creating adapter to pager
        mPagerAdapter = new TabPagerExerciseDetailAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to view pager
        viewpager.setAdapter(mPagerAdapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);

        selectPage(defaultIndexPager);

    }

    public View getTabView(int imageResource) {
        View view = LayoutInflater.from(ExerciseMuscleDetailActivity.this).inflate(R.layout.custom_main_tab_layout, null);
        final ImageView imageView = view.findViewById(R.id.img_tab);//img_tab in custom_main_tab_layout
        imageView.setImageResource(imageResource);

        view.setPadding(0, 0, 0, 0);
        return view;

    }

    private void selectPage(int pageIndex) {
        TabLayout.Tab tab = tabLayout.getTabAt(pageIndex);
        tab.select();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewpager.setCurrentItem(tab.getPosition(), false);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
