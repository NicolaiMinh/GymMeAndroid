package com.android.sgvn.gymme.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.activities.ExerciseMuscleActivity;
import com.android.sgvn.gymme.common.Common;
import com.android.sgvn.gymme.model.ExerciseMuscleDetail;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by sgvn144 on 2018/05/11.
 */

public class ExerciseMuscleRecyclerAdapter extends RecyclerView.Adapter<ExerciseMuscleRecyclerAdapter.ExerciseMuscleRecyclerHolder> {

    private Context context;
    private List<ExerciseMuscleDetail> exerciseMuscleDetailList;
    private ExerciseMuscleRecyclerHolder.ClickListener mClickListener;

    //firebase
    FirebaseDatabase database;
    DatabaseReference reference;

    boolean isSetFavorite;

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
    public void onBindViewHolder(final ExerciseMuscleRecyclerHolder holder, int position) {
        final ExerciseMuscleDetail item = exerciseMuscleDetailList.get(position);

        //set Image
        Glide.with(context)
                .load(item.getImageURL())
                .into(holder.mImageView);
        //set exercise name
        holder.titleExerciseName.setText(item.getExerciseName());


        //set favorite icon
//        final Intent intent = new Intent(context, ExerciseMuscleActivity.class);
        holder.favoriteCardMuscle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!item.isFavourite()) {
                    holder.favoriteCardMuscle.setImageResource(R.drawable.ic_star_black_favorite);
                    item.setFavourite(true);
                    isSetFavorite = true;
//                    intent.putExtra(Common.EXERCISE_POSITION_FAVORITE, holder.getAdapterPosition());

                } else {
                    holder.favoriteCardMuscle.setImageResource(R.drawable.ic_star_black_item);
                    item.setFavourite(false);
                    isSetFavorite = false;
//                    intent.putExtra(Common.EXERCISE_POSITION_FAVORITE,holder.getAdapterPosition());
                }
                notifyDataSetChanged();
//                context.startActivity(intent);
                updateFavoriteToDatabase();
            }
        });


    }

    private void updateFavoriteToDatabase() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference(Common.FIREBASE_MUSCLE_EXERCISE_TABLE);
        reference.child(Common.FIREBASE_MUSCLE_EXERCISE_CHEST_TABLE).child("1").child("isFavourite").setValue(isSetFavorite);
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
        private ImageView mImageView, favoriteCardMuscle;
        private TextView titleExerciseName;


        public ExerciseMuscleRecyclerHolder(View itemView, ClickListener listener) {
            super(itemView);
            mListener = listener;
            itemView.setOnClickListener(this);

            //view in custom_card_muscle exercise
            mImageView = itemView.findViewById(R.id.image_card_exercise);
            favoriteCardMuscle = itemView.findViewById(R.id.favoriteCardMuscle);
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
