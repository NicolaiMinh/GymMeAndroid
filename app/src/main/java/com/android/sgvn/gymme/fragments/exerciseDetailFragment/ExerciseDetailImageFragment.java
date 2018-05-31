package com.android.sgvn.gymme.fragments.exerciseDetailFragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.fragments.BaseFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExerciseDetailImageFragment extends BaseFragment {
    private static final String TAG = ExerciseDetailImageFragment.class.getSimpleName();

    @BindView(R.id.imageView)
    ImageView imageView;
    Unbinder unbinder;
    List<String> receiveData;

    String imageURL;

    public ExerciseDetailImageFragment() {
        // Required empty public constructor
    }

    public void addData(List<String> receiveData){
        this.receiveData =  receiveData;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise_detail_image, container, false);
        unbinder = ButterKnife.bind(this, view);
        imageURL = receiveData.get(5);//Common.EXERCISE_DETAIL_IMAGE

        Log.d(TAG + " onCreateView ", imageURL);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(view != null){
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
            Glide.with(this).load(imageURL).apply(requestOptions).into(imageView);
        }
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
