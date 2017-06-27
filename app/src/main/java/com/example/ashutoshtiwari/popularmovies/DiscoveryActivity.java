package com.example.ashutoshtiwari.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoveryActivity extends AppCompatActivity implements AbsListView.OnScrollListener {

    private static final int SORT_OPERATION = 1;
    private static final int SORT_POP = 0;
    private static final int SORT_TOP = 1;
    private int PAGE = 1;
    private static int SORT_CURRENT = SORT_POP;
    private final String TAG = DiscoveryActivity.class.getSimpleName();
    private GridView gridView;
    private TheMovieDB theMovieDB;
    private ArrayList<TheMovieDB> movies = new ArrayList<>();
    private MoviesAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);

        gridView = (GridView) findViewById(R.id.gridview);
        updateUI(1);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                theMovieDB = movies.get(position);
                Intent intent = new Intent(DiscoveryActivity.this, MovieDetailActivity.class);
                intent.putExtra("movie", theMovieDB); //Sending movie data for a specific movie, that is clicked by the user
                startActivity(intent);
            }
        });
        gridView.setOnScrollListener(this);
        //setOnCustomerScrollListener(gridView, gridView.getLastVisiblePosition(), movies.size());
    }

    /*private void setOnCustomerScrollListener(GridView gridView, int lastVisiblePosition, int totalMovies) {
        if ()
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, SORT_OPERATION);
            return true;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String sortValue = "";

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sortValue = sharedPreferences.getString(getString(R.string.pref_sort_key), getString(R.string.action_sort_by_popular));

        if (requestCode == SORT_OPERATION) {
            if (resultCode == RESULT_OK) {
                if (sortValue.equals(getString(R.string.action_sort_by_popular))) {
                    if (SORT_CURRENT == SORT_TOP) {
                        sortImages(SORT_POP);
                        SORT_CURRENT = SORT_POP;
                    }
                }
                if (sortValue.equals(getString(R.string.action_sort_by_top_rated))) {
                    if (SORT_CURRENT == SORT_POP) {
                        sortImages(SORT_TOP);
                        SORT_CURRENT = SORT_TOP;
                    }
                }
            }
        }
    }

    private void sortImages(int sortOrder) {
        if (sortOrder == SORT_POP) { //Sorting based on Popularity
            Collections.sort(movies, new Comparator<TheMovieDB>() {
                @Override
                public int compare(TheMovieDB o1, TheMovieDB o2) {
                    return Double.compare(o2.getPopularity(), o1.getPopularity());
                }
            });
        }
        if (sortOrder == SORT_TOP) { // Sorting based on User ratings
            Collections.sort(movies, new Comparator<TheMovieDB>() {
                @Override
                public int compare(TheMovieDB o1, TheMovieDB o2) {
                    return Double.compare(o2.getVoteAverage(), o1.getVoteAverage());
                }
            });
        }

        if (adapter != null) {
            adapter.notifyDataSetChanged(); // Refreshing the gridView from its adapter.
        } else {
            Log.v("DISCOVERY ACTIVITY", "Adapter is empty, check it out.");
        }
    }


    private void updateUI(int page) {

        Log.v("DiscoveryActivity", "API called");

        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TMDClient tmdClient = retrofit.create(TMDClient.class);*/

        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();
        Call<MoviesData> call = retrofitBuilder.tmdClient.getMovies("ff2b1318ca9429b7117efd20171d482d", page); //Setting the APIKey
        call.enqueue(new Callback<MoviesData>() {
            @Override
            public void onResponse(@NonNull Call<MoviesData> call, @NonNull Response<MoviesData> response) {
                if (response.isSuccessful()) {

                    movies.addAll(response.body().getResults());

                    if (adapter == null) {
                        adapter = new MoviesAdapter(getApplicationContext(), movies);
                        gridView.setAdapter(adapter);//Set the images using Picasso on the gridView
                    } else {
                        adapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onFailure(Call<MoviesData> call, Throwable t) {
                Log.d(TAG, t.toString());
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onScrollStateChanged(AbsListView gridView, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView gridView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (gridView.getLastVisiblePosition() == totalItemCount - 1) {
            updateUI(PAGE++);
        }
    }
}
