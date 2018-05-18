package com.android.sgvn.gymme.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.common.Common;

public class MealDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_detail);

        TextView title_meal = findViewById(R.id.title_meal);
        title_meal.setText(getIntent().getStringExtra(Common.TITLE_MEAL));


        final Button testButton = findViewById(R.id.testButton);
        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.aaa);
        final View view = findViewById(R.id.imageView);
        testButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                testButton.startAnimation(anim);
            }
        });
    }
}
