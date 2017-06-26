package com.example.ashutoshtiwari.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DiscoveryActivity extends AppCompatActivity {

    private static final int SORT_OPERATION = 1;
    private static final int SORT_POP = 0;
    private static final int SORT_TOP = 1;
    private static int SORT_CURRENT = 0;
    private final String TAG = DiscoveryActivity.class.getSimpleName();
    private GridView gridView;
    private TheMovieDB theMovieDB;
    private List<TheMovieDB> movies;
    private ArrayList<String> imagePath = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);

        gridView = (GridView) findViewById(R.id.gridview);
        updateUI();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                theMovieDB = movies.get(position);
                Intent intent = new Intent(DiscoveryActivity.this, MovieDetailActivity.class);
                intent.putExtra("movie", theMovieDB);
                startActivity(intent);
            }
        });
    }

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

        if (requestCode == SORT_OPERATION) {
            if (resultCode == RESULT_OK) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                sortValue = sharedPreferences.getString(getString(R.string.pref_sort_key), getString(R.string.action_sort_by_popular));
            }
        }

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

    private void sortImages(int sortOrder) {
        if (sortOrder == SORT_POP) {
            Collections.sort(movies, new Comparator<TheMovieDB>() {
                @Override
                public int compare(TheMovieDB o1, TheMovieDB o2) {
                    return Double.compare(o1.getPopularity(), o2.getPopularity());
                }
            });
        }
        if (sortOrder == SORT_TOP) {
            Collections.sort(movies, new Comparator<TheMovieDB>() {
                @Override
                public int compare(TheMovieDB o1, TheMovieDB o2) {
                    return Double.compare(o1.getVoteAverage(), o2.getVoteAverage());
                }
            });
        }

        for (int i = 0; i < movies.size(); i++) {
            imagePath.add(i, movies.get(i).getPosterPath());
        }

        gridView.setAdapter(new ImageAdapter(getApplicationContext(), imagePath));
    }

    private void updateUI() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TMDClient tmdClient = retrofit.create(TMDClient.class);
        Call<MoviesData> call = tmdClient.getMovies("APIKEY");
        call.enqueue(new Callback<MoviesData>() {
            @Override
            public void onResponse(@NonNull Call<MoviesData> call, @NonNull Response<MoviesData> response) {
                if (response.isSuccessful()) {

                    movies = response.body().getResults();
                    for (int i = 0; i < movies.size(); i++) {
                        imagePath.add(i, movies.get(i).getPosterPath());
                    }
                    gridView.setAdapter(new ImageAdapter(getApplicationContext(), imagePath));
                }
            }

            @Override
            public void onFailure(Call<MoviesData> call, Throwable t) {
                Log.d(TAG, t.toString());
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });
    }
}
