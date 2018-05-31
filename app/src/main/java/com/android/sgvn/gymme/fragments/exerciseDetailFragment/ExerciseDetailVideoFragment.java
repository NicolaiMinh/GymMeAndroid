package com.android.sgvn.gymme.fragments.exerciseDetailFragment;


import android.app.Fragment;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.fragments.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExerciseDetailVideoFragment extends BaseFragment {
    private static final String TAG = ExerciseDetailVideoFragment.class.getSimpleName();
    List<String> receiveData;
    @BindView(R.id.video_view)
    VideoView videoView;
    Unbinder unbinder;

    String videoURL;

    public ExerciseDetailVideoFragment() {
        // Required empty public constructor
    }

    //add Data from adapter
    public void addData(List<String> receiveData) {
        this.receiveData = receiveData;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise_detail_video, container, false);
        unbinder = ButterKnife.bind(this, view);
        videoURL = receiveData.get(4);//Common.EXERCISE_DETAIL_VIDEO_URL
        Log.d(TAG + " onCreateView ", videoURL);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view != null) {
            //Creating MediaController
            MediaController mediaController = new MediaController(getActivity());
            mediaController.setAnchorView(videoView);
            //set media control is hide
            mediaController.setVisibility(View.INVISIBLE);

            //specify the location of media file
            Uri uri = Uri.parse(videoURL);

            //Setting MediaController and URI, then starting the videoView
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(uri);
            videoView.requestFocus();
            videoView.start();
            //set video play looping
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    videoView.start();//repeat loop
                }
            });
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
