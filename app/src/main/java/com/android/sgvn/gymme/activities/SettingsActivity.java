package com.android.sgvn.gymme.activities;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.android.sgvn.gymme.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        //setup toolbar
        setupToolbar();
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
        switch (item.getItemId()){
            case android.R.id.home: //nut home la co san trong he thong
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
