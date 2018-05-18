package com.android.sgvn.gymme.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.model.Meal;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by sgvn144 on 2018/04/26.
 */

public class MealListAdapter extends RecyclerView.Adapter<MealListAdapter.MealListAdapterViewHolder> {

    private Context context;
    private List<Meal> mealList;

    private MealListAdapterViewHolder.ClickListener mClickListener;

    public MealListAdapter(Context context, List<Meal> mealList, MealListAdapterViewHolder.ClickListener mClickListener) {
        this.context = context;
        this.mealList = mealList;
        this.mClickListener = mClickListener;
    }

    @Override
    public MealListAdapter.MealListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        //layout custom card meal
        View itemView = layoutInflater.inflate(R.layout.custom_card_meal, parent, false);


        return new MealListAdapterViewHolder(itemView, mClickListener);
    }

    @Override
    public void onBindViewHolder(MealListAdapter.MealListAdapterViewHolder holder, int position) {
        Meal meal = mealList.get(position);

        Glide.with(context)
                .load(meal.getImageURL())
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mealList == null ? 0 : mealList.size();
    }

    public List<Meal> getMeal() {
        return mealList;
    }

    public static class MealListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ClickListener mListener;
        private ImageView mImageView;


        public MealListAdapterViewHolder(View itemView, ClickListener listener) {
            super(itemView);
            mListener = listener;
            itemView.setOnClickListener(this);

            //view in custom_card_meal
            mImageView = itemView.findViewById(R.id.image_card_meal);
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
