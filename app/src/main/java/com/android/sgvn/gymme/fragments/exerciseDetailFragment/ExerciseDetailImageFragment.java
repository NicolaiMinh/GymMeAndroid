package com.android.sgvn.gymme.fragments.exerciseDetailFragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.fragments.BaseFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExerciseDetailImageFragment extends BaseFragment {


    @BindView(R.id.imageView)
    ImageView imageView;
    Unbinder unbinder;

    public ExerciseDetailImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise_detail_image, container, false);
        unbinder = ButterKnife.bind(this, view);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(this).load("https://media.giphy.com/media/fQGDutVwP7ktZUwojJ/giphy.gif").apply(requestOptions).into(imageView);

        return view;
    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
