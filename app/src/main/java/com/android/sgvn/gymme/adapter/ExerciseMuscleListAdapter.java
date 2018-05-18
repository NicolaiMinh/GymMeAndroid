package com.android.sgvn.gymme.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.model.ExerciseMuscle;

import java.util.List;

/**
 * Created by sgvn144 on 2018/05/11.
 */

public class ExerciseMuscleListAdapter extends ArrayAdapter<ExerciseMuscle> {

    Context context;
    int resID;
    private List<ExerciseMuscle> exerciseMuscleList;
    ItemClickListener mItemClickListener;

    public ExerciseMuscleListAdapter(@NonNull Context context, int resID, List<ExerciseMuscle> exerciseMuscleList) {
        super(context, resID, exerciseMuscleList);
        this.context = context;
        this.resID = resID;
        this.exerciseMuscleList = exerciseMuscleList;
    }

    public void setmItemClickListener(ItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(resID, null);

        TextView muscleExerciseText = view.findViewById(R.id.muscleExerciseText);

        muscleExerciseText.setText(exerciseMuscleList.get(position).getExerciseMuscleName());


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mItemClickListener.onClick(position);
            }
        });

        return view;
    }

    public interface ItemClickListener{
        void onClick(int position);
    }
}
