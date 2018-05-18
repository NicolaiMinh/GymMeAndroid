package com.android.sgvn.gymme.fragments.tabMainFragments;


import android.app.Fragment;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.activities.MealDetailActivity;
import com.android.sgvn.gymme.adapter.MealListAdapter;
import com.android.sgvn.gymme.common.Common;
import com.android.sgvn.gymme.fragments.BaseFragment;
import com.android.sgvn.gymme.model.Meal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MealPlanFragment extends BaseFragment implements MealListAdapter.MealListAdapterViewHolder.ClickListener {
    private static final String TAG = MealPlanFragment.class.getSimpleName();
    @BindView(R.id.recycler_view_meal)
    RecyclerView recyclerViewMeal;
    Unbinder unbinder;

    MealListAdapter mAdapter;
    private List<Meal> mealList;

    //firebase
    FirebaseDatabase database;
    DatabaseReference reference;

    public MealPlanFragment() {
        // Required empty public constructor
        database = FirebaseDatabase.getInstance();
        reference = database.getReference(Common.FIREBASE_MEAL_TABLE);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_plan, container, false);
        unbinder = ButterKnife.bind(this, view);

        mealList = new ArrayList<>();
        mAdapter = new MealListAdapter(getContext(), mealList, this);

        recyclerViewMeal.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewMeal.setLayoutManager(linearLayoutManager);
        recyclerViewMeal.setAdapter(mAdapter);
        getDataFromFirebase();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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


    //on click each item
    @Override
    public void onCLickItem(int position) {
        Log.d(TAG, "position " + mAdapter.getMeal().get(position));

        if(mAdapter != null){
            Intent intent = new Intent(getActivity(), MealDetailActivity.class);
            intent.putExtra(Common.TITLE_MEAL, mAdapter.getMeal().get(position).getMealName());
            getActivity().startActivity(intent);
        }
    }


    private void getDataFromFirebase() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Meal meal = snapshot.getValue(Meal.class);

                    mealList.add(meal);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Database error: " + databaseError.getMessage());
            }
        });
    }
}
