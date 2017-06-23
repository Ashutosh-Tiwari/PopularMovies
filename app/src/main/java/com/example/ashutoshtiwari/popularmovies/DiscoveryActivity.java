package com.example.ashutoshtiwari.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DiscoveryActivity extends AppCompatActivity {

    private final String TAG = DiscoveryActivity.class.getSimpleName();
    private GridView gridView;
    private TheMovieDB theMovieDB;
    private List<TheMovieDB> movies;

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
                intent.putExtra("item_position", position);
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

    private void updateUI() {

        final ArrayList<String> imagePath = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TMDClient tmdClient = retrofit.create(TMDClient.class);
        Call<MoviesData> call = tmdClient.getMovies("ReplaceWithKey");
        call.enqueue(new Callback<MoviesData>() {
            @Override
            public void onResponse(@NonNull Call<MoviesData> call, @NonNull Response<MoviesData> response) {
                if (response.isSuccessful()) {
                    movies = response.body().getResults();
                    int i = 0;
                    while (i < movies.size()) {
                        imagePath.add(i, movies.get(i).getPosterPath());
                        i++;
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
