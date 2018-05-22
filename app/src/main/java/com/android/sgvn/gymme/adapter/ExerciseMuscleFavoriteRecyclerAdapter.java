package com.android.sgvn.gymme.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.model.ExerciseMuscleDetail;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sgvn144 on 2018/05/11.
 */

public class ExerciseMuscleFavoriteRecyclerAdapter extends RecyclerView.Adapter<ExerciseMuscleFavoriteRecyclerAdapter.ExerciseMuscleFavoriteRecyclerHolder>  implements Filterable {

    private Context context;
    private List<ExerciseMuscleDetail> exerciseMuscleDetailList;
    private List<ExerciseMuscleDetail> exerciseMuscleDetailListFiltered;//list for filter
    private ExerciseMuscleFavoriteRecyclerHolder.ClickListener mClickListener;

    public ExerciseMuscleFavoriteRecyclerAdapter(Context context, List<ExerciseMuscleDetail> exerciseMuscleDetailList, ExerciseMuscleFavoriteRecyclerHolder.ClickListener mClickListener) {
        this.context = context;
        this.exerciseMuscleDetailList = exerciseMuscleDetailList;
        this.exerciseMuscleDetailListFiltered = exerciseMuscleDetailList;
        this.mClickListener = mClickListener;
    }

    @Override
    public ExerciseMuscleFavoriteRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_card_muscle_exercise, parent, false);

        return new ExerciseMuscleFavoriteRecyclerHolder(view, mClickListener);
    }

    @Override
    public void onBindViewHolder(final ExerciseMuscleFavoriteRecyclerHolder holder, int position) {
        final ExerciseMuscleDetail item = exerciseMuscleDetailListFiltered.get(position);

        //set Image
        Glide.with(context)
                .load(item.getImageURL())
                .into(holder.mImageView);
        //set exercise name
        holder.titleExerciseName.setText(item.getExerciseName());

        if (item.isFavorite()) {
            holder.favoriteCardMuscle.setImageResource(R.drawable.ic_star_black_favorite);
        } else {
            holder.favoriteCardMuscle.setImageResource(R.drawable.ic_star_black_item);
        }
    }

    @Override
    public int getItemCount() {
        return exerciseMuscleDetailListFiltered == null ? 0 : exerciseMuscleDetailListFiltered.size();
    }

    public List<ExerciseMuscleDetail> getExerciseMuscleDetail() {
        return exerciseMuscleDetailListFiltered;
    }

    //filter in favorite list
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    exerciseMuscleDetailListFiltered = exerciseMuscleDetailList;
                } else {
                    List<ExerciseMuscleDetail> filteredList = new ArrayList<>();
                    for (ExerciseMuscleDetail row : exerciseMuscleDetailList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getExerciseName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    exerciseMuscleDetailListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = exerciseMuscleDetailListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                exerciseMuscleDetailListFiltered = (ArrayList<ExerciseMuscleDetail>) filterResults.values;

                if (exerciseMuscleDetailListFiltered.size() == 0) {
//                    Toast.makeText(context, "Ko có giá tri tim kiem", Toast.LENGTH_SHORT).show();
                } else {

                    notifyDataSetChanged();
                }
            }
        };
    }

    public static class ExerciseMuscleFavoriteRecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ClickListener mListener;
        public ImageView mImageView, favoriteCardMuscle;
        private TextView titleExerciseName;


        public ExerciseMuscleFavoriteRecyclerHolder(View itemView, ClickListener listener) {
            super(itemView);
            mListener = listener;
            itemView.setOnClickListener(this);

            //view in custom_card_muscle exercise
            mImageView = itemView.findViewById(R.id.image_card_exercise);
            favoriteCardMuscle = itemView.findViewById(R.id.favoriteCardMuscle);
            titleExerciseName = itemView.findViewById(R.id.titleExerciseName);

            favoriteCardMuscle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onFavoriteClickFavoriteItem(getAdapterPosition());
                }
            });
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onFavoriteClickItem(getAdapterPosition());
            }
        }

        //click on each item
        public interface ClickListener {
            void onFavoriteClickItem(int position);//click card

            void onFavoriteClickFavoriteItem(int position);//click item favorite
        }

    }
}
