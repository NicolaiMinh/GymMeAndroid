package com.android.sgvn.gymme.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.model.ExerciseMuscleDetail;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Created by sgvn144 on 2018/06/05.
 */

public class WorkoutRecyclerAdapter extends RecyclerView.Adapter<WorkoutRecyclerAdapter.WorkoutRecyclerHolder> {

    private Context context;
    private List<ExerciseMuscleDetail> exerciseMuscleDetailList;
    private WorkoutRecyclerHolder.ClickListener mClickListener;

    public WorkoutRecyclerAdapter(Context context, List<ExerciseMuscleDetail> exerciseMuscleDetailList, WorkoutRecyclerHolder.ClickListener mClickListener) {
        this.context = context;
        this.exerciseMuscleDetailList = exerciseMuscleDetailList;
        this.mClickListener = mClickListener;
    }

    @Override
    public WorkoutRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        //layout custom card meal
        View itemView = layoutInflater.inflate(R.layout.custom_exercise_workout_list, parent, false);

        return new WorkoutRecyclerHolder(itemView, mClickListener);
    }

    @Override
    public void onBindViewHolder(WorkoutRecyclerHolder holder, int position) {
        ExerciseMuscleDetail muscleDetail = exerciseMuscleDetailList.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context)
                .load(muscleDetail.getImageURL())
                .apply(requestOptions)
                .into(holder.mImageView);
        holder.titleExerciseName.setText(muscleDetail.getExerciseName());
    }

    @Override
    public int getItemCount() {
        return exerciseMuscleDetailList == null ? 0 : exerciseMuscleDetailList.size();
    }

    public List<ExerciseMuscleDetail> getExerciseMuscleDetail() {
        return exerciseMuscleDetailList;
    }

    public static class WorkoutRecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private WorkoutRecyclerAdapter.WorkoutRecyclerHolder.ClickListener mListener;
        private ImageView mImageView;
        private TextView titleExerciseName;


        public WorkoutRecyclerHolder(View itemView, WorkoutRecyclerAdapter.WorkoutRecyclerHolder.ClickListener listener) {
            super(itemView);
            mListener = listener;
            itemView.setOnClickListener(this);

            //view in custom_card_muscle exercise
            mImageView = itemView.findViewById(R.id.exerciseImage);
            titleExerciseName = itemView.findViewById(R.id.exerciseName);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onClickItem(getAdapterPosition());
            }
        }

        //click on each item
        public interface ClickListener {
            void onClickItem(int position);
        }

    }

}
