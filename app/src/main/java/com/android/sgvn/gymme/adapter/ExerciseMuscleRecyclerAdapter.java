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
import com.android.sgvn.gymme.model.Meal;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by sgvn144 on 2018/05/11.
 */

public class ExerciseMuscleRecyclerAdapter extends RecyclerView.Adapter<ExerciseMuscleRecyclerAdapter.ExerciseMuscleRecyclerHolder> {

    private Context context;
    private List<ExerciseMuscleDetail> exerciseMuscleDetailList;
    private ExerciseMuscleRecyclerHolder.ClickListener mClickListener;

    public ExerciseMuscleRecyclerAdapter(Context context, List<ExerciseMuscleDetail> exerciseMuscleDetailList, ExerciseMuscleRecyclerHolder.ClickListener mClickListener) {
        this.context = context;
        this.exerciseMuscleDetailList = exerciseMuscleDetailList;
        this.mClickListener = mClickListener;
    }

    @Override
    public ExerciseMuscleRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_card_muscle_exercise, parent, false);


        return new ExerciseMuscleRecyclerHolder(view, mClickListener);
    }

    @Override
    public void onBindViewHolder(ExerciseMuscleRecyclerHolder holder, int position) {
        ExerciseMuscleDetail item = exerciseMuscleDetailList.get(position);

        //set Image
        Glide.with(context)
                .load(item.getImageURL())
                .into(holder.mImageView);
        //set exercise name
        holder.titleExerciseName.setText(item.getExerciseName());
    }

    @Override
    public int getItemCount() {
        return exerciseMuscleDetailList == null ? 0 : exerciseMuscleDetailList.size();
    }

    public List<ExerciseMuscleDetail> getExerciseMuscleDetail() {
        return exerciseMuscleDetailList;
    }

    public static class ExerciseMuscleRecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ClickListener mListener;
        private ImageView mImageView;
        private TextView titleExerciseName;


        public ExerciseMuscleRecyclerHolder(View itemView, ClickListener listener) {
            super(itemView);
            mListener = listener;
            itemView.setOnClickListener(this);

            //view in custom_card_muscle exercise
            mImageView = itemView.findViewById(R.id.image_card_exercise);
            titleExerciseName = itemView.findViewById(R.id.titleExerciseName);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onCLickItem(getAdapterPosition());
            }
        }


        //click on each item
        public interface ClickListener {
            void onCLickItem(int position);
        }

    }
}
