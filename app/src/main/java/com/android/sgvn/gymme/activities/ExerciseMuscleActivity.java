package com.android.sgvn.gymme.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.adapter.ExerciseMuscleRecyclerAdapter;
import com.android.sgvn.gymme.common.Common;
//import com.android.sgvn.gymme.database.modelDB.ExerciseModel;
import com.android.sgvn.gymme.fragments.exerciseDetailFragment.ExerciseDetailInfoFragment;
import com.android.sgvn.gymme.model.ExerciseMuscleDetail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ExerciseMuscleActivity extends BaseActivity implements ExerciseMuscleRecyclerAdapter.ExerciseMuscleRecyclerHolder.ClickListener {

    private static final String TAG = ExerciseMuscleActivity.class.getSimpleName();

    @BindView(R.id.list_muscle_exercise)
    RecyclerView listMuscleExercise;
    @BindView(R.id.favoriteEachExercise)
    ImageView favoriteEachExercise;
    @BindView(R.id.message_favorite)
    TextView messageFavorite;

    private SearchView searchView;

    private ExerciseMuscleRecyclerAdapter mAdapter;

    private List<ExerciseMuscleDetail> exerciseMuscleDetailList;
    private List<ExerciseMuscleDetail> exerciseMuscleDetailListTemp;
    private boolean isSetFavorite;
    private boolean isFetchData = false;
    private boolean isChooseFavorite = false;
    private ExerciseMuscleDetail currentPosition;
    String idExercise, nameExercise;


    //firebase
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_muscle);
        ButterKnife.bind(this);

        idExercise = getIntent().getStringExtra("idExercise");
        nameExercise = getIntent().getStringExtra("nameExercise");
        Log.d(TAG, idExercise + " " + nameExercise);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference(Common.FIREBASE_MUSCLE_EXERCISE_TABLE);

        initView();
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

    }

    private void initView() {
        isChooseFavorite = false;
        favoriteEachExercise.setImageResource(R.drawable.ic_star_border_yellow_24dp);
        exerciseMuscleDetailList = new ArrayList<>();
        exerciseMuscleDetailListTemp = new ArrayList<>();
        exerciseMuscleDetailListTemp.clear();
        mAdapter = new ExerciseMuscleRecyclerAdapter(this, exerciseMuscleDetailListTemp, this);
        listMuscleExercise.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);//hien thi 2 cot
        listMuscleExercise.setLayoutManager(gridLayoutManager);
        listMuscleExercise.setAdapter(mAdapter);
        if (nameExercise != null) {
            showProgressDialog();
            getDataFromFirebase();
        }
    }

    private void getDataFromFirebase() {
        reference.child(nameExercise).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ExerciseMuscleDetail muscleDetail = snapshot.getValue(ExerciseMuscleDetail.class);
                    exerciseMuscleDetailList.add(muscleDetail);
//                    //saveDataToDatabase();
//                    try {
//                        ExerciseModel exerciseModel = new ExerciseModel();
//                        exerciseModel.execution = muscleDetail.getExecution().toString();
//                        exerciseModel.exerciseDetail = muscleDetail.getExerciseDetail().toString();
//                        exerciseModel.exerciseName = muscleDetail.getExerciseName().toString();
//                        exerciseModel.imageURL = muscleDetail.getImageURL().toString();
//                        exerciseModel.primaryMuscle = muscleDetail.getPrimaryMuscle().toString();
//                        exerciseModel.preparation = muscleDetail.getPreparation().toString();
//                        exerciseModel.secondaryMucsle = muscleDetail.getSecondaryMucsle().toString();
//                        exerciseModel.videoURL = muscleDetail.getVideoURL().toString();
//                        exerciseModel.favorite = muscleDetail.isFavorite();
//                        //add to database
//                        Common.exerciseModelRepository.insertToExerciseModel(exerciseModel);
//                        Log.d(TAG, new Gson().toJson(exerciseModel));
//                        Toast.makeText(ExerciseMuscleActivity.this, "Insert to database success", Toast.LENGTH_SHORT).show();
//
//                    } catch (Exception e) {
//                        Toast.makeText(ExerciseMuscleActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
                }
                exerciseMuscleDetailListTemp.addAll(exerciseMuscleDetailList);
                if (exerciseMuscleDetailListTemp.size() == 0) {
                    messageFavorite.setText("There is have not data in the '" + nameExercise + "' category.");
                    messageFavorite.setVisibility(View.VISIBLE);
                    isFetchData = true;
                }
                mAdapter.notifyDataSetChanged();
                dismissProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Database error: " + databaseError.getMessage());
            }
        });
    }


    //implements ExerciseMuscleRecyclerAdapter.ExerciseMuscleRecyclerHolder.ClickListener
    //onclick card view
    @Override
    public void onClickItem(final int position) {
        Log.d(TAG, "position " + mAdapter.getExerciseMuscleDetail().get(position));
        if (mAdapter != null) {
            Intent intent = new Intent(this, ExerciseMuscleDetailActivity.class);
            intent.putExtra(Common.EXERCISE_DETAIL_NAME, mAdapter.getExerciseMuscleDetail().get(position).getExerciseName());
            intent.putExtra(Common.EXERCISE_DETAIL_FAVORITE, mAdapter.getExerciseMuscleDetail().get(position).isFavorite());
            intent.putExtra(Common.EXERCISE_DETAIL_IMAGE, mAdapter.getExerciseMuscleDetail().get(position).getImageURL());
            intent.putExtra(Common.EXERCISE_DETAIL_VIDEO_URL, mAdapter.getExerciseMuscleDetail().get(position).getVideoURL());
            intent.putExtra(Common.EXERCISE_DETAIL_EXECUTION, mAdapter.getExerciseMuscleDetail().get(position).getExecution());
            intent.putExtra(Common.EXERCISE_DETAIL_PREPARATION, mAdapter.getExerciseMuscleDetail().get(position).getPreparation());
            intent.putExtra(Common.EXERCISE_DETAIL_PRIMARY_MUSCLE, mAdapter.getExerciseMuscleDetail().get(position).getPrimaryMuscle());
            intent.putExtra(Common.EXERCISE_DETAIL_SECONDARY_MUSCLE, mAdapter.getExerciseMuscleDetail().get(position).getSecondaryMucsle());
            startActivity(intent);
        }

    }

    //click item favorite
    @Override
    public void onClickFavoriteItem(int position) {
        if (mAdapter.getExerciseMuscleDetail().get(position).isFavorite()) {
            mAdapter.getExerciseMuscleDetail().get(position).setFavorite(false);
            isSetFavorite = false;
        } else {
            mAdapter.getExerciseMuscleDetail().get(position).setFavorite(true);
            isSetFavorite = true;
        }
        currentPosition = mAdapter.getExerciseMuscleDetail().get(position);
        uploadSetFavorite(isSetFavorite);
        mAdapter.notifyDataSetChanged();

        if (isChooseFavorite) {
            //when click dislike  exerciseMuscleDetailList remove item with position in list
            exerciseMuscleDetailListTemp.remove(position);
            //recycler remove item in list
            listMuscleExercise.removeViewAt(position);
            //adapter is notifyItemRemoved
            mAdapter.notifyItemRemoved(position);
            //adapter is update range of list
            mAdapter.notifyItemRangeChanged(position, exerciseMuscleDetailListTemp.size());

            mAdapter.notifyDataSetChanged();

            //when unlike in list favorite
            if (exerciseMuscleDetailListTemp.size() == 0) {
                messageFavorite.setText("You have not selected any favorites in the '" + nameExercise + "' category.");
                messageFavorite.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void notifyTextChanged(String textSearch) {
        if (mAdapter.getExerciseMuscleDetail().size() == 0 && !textSearch.isEmpty()) {
            messageFavorite.setText("No results for '" + textSearch + "' in " + nameExercise + " category. \n Note: the search covers bodyparts, equipment, muscle exercise.");
            messageFavorite.setVisibility(View.VISIBLE);
        } else {
            messageFavorite.setText("");
            messageFavorite.setVisibility(View.INVISIBLE);
        }
        if (isChooseFavorite && textSearch.isEmpty() && mAdapter.getExerciseMuscleDetail().size() == 0) {
            messageFavorite.setText("You have not selected any favorites in the '" + nameExercise + "' category.");
            messageFavorite.setVisibility(View.VISIBLE);
        }
        if (!isChooseFavorite && textSearch.isEmpty() && isFetchData) {
            messageFavorite.setText("There is have not data in the '" + nameExercise + "' category.");
            messageFavorite.setVisibility(View.VISIBLE);
        }
    }

    private void uploadSetFavorite(final boolean isSetFavorite) {
        HashMap<String, Object> params = new HashMap<>();
        params.put(Common.EXERCISE_SET_FAVORITE_PROPERTY, isSetFavorite);
        // get current position by ID and update
        if (!nameExercise.isEmpty()) {
            reference.child(nameExercise).child(String.valueOf(currentPosition.getId())).updateChildren(params);
            mAdapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.favoriteEachExercise})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.favoriteEachExercise:
                fetchExerciseFavorite();
                break;
        }
    }

    private void fetchExerciseFavorite() {
        if (!isChooseFavorite) {
            isChooseFavorite = true;
            favoriteEachExercise.setImageResource(R.drawable.ic_star_yellow_24dp);
        } else {
            isChooseFavorite = false;
            messageFavorite.setVisibility(View.INVISIBLE);
            favoriteEachExercise.setImageResource(R.drawable.ic_star_border_yellow_24dp);
            if (exerciseMuscleDetailListTemp.size() == 0 && isFetchData) {
                messageFavorite.setText("There is have not data in the '" + nameExercise + "' category.");
                messageFavorite.setVisibility(View.VISIBLE);
                isFetchData = true;
            }
        }

        //before fetch data
        if (nameExercise != null) {
            getFavoriteItemFromListAll();
        }

    }

    private void getFavoriteItemFromListAll() {
        exerciseMuscleDetailListTemp.clear();
        for (ExerciseMuscleDetail muscleDetail : exerciseMuscleDetailList) {
            if (isChooseFavorite == false || muscleDetail.isFavorite()) {
                exerciseMuscleDetailListTemp.add(muscleDetail);
            }
        }
        if (exerciseMuscleDetailListTemp.size() == 0 && isChooseFavorite) {
            messageFavorite.setText("You have not selected any favorites in the '" + nameExercise + "' category.");
            messageFavorite.setVisibility(View.VISIBLE);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }


}
