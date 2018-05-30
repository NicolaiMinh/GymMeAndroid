package com.android.sgvn.gymme.fragments.exerciseDetailFragment;


import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.fragments.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExerciseDetailVideoFragment extends BaseFragment {


    @BindView(R.id.video_view)
    VideoView videoView;
    Unbinder unbinder;

    public ExerciseDetailVideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise_detail_video, container, false);
        unbinder = ButterKnife.bind(this, view);
        //Creating MediaController
        MediaController mediaController= new MediaController(getActivity());
        mediaController.setAnchorView(videoView);

        //specify the location of media file
        Uri uri= Uri.parse("https://firebasestorage.googleapis.com/v0/b/gymme-f9bc3.appspot.com/o/Video%2FLeg%2Fleg_press.mp4?alt=media&token=b3ad34c1-a462-47e7-b697-52a797eeded6");

        //Setting MediaController and URI, then starting the videoView
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();

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
