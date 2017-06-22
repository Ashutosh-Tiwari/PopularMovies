package com.example.ashutoshtiwari.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DiscoveryActivity extends AppCompatActivity {

    private GridView gridView;
    private ArrayList<Integer> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);

        updateUI();
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new ImageAdapter(this, images));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DiscoveryActivity.this, MovieDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateUI() {
        images = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TMDClient tmdClient = retrofit.create(TMDClient.class);
        Call<MoviesData> call = tmdClient.getMovies("ReplaceWithAPIKey");
        call.enqueue(new Callback<MoviesData>() {
            @Override
            public void onResponse(@NonNull Call<MoviesData> call, @NonNull Response<MoviesData> response) {
                MoviesData body = response.body();
                //Log.v("DiscoveryActivity.java", "Response: " + body.getResults().toString());
                //body.getResults().get(0).getGenreIds().get(0);
                //ArrayList<String> moviesThumbnailPath = body.getResults().

            }

            @Override
            public void onFailure(Call<MoviesData> call, Throwable t) {
                Log.d("Discovery", t.toString());
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });
    }
}
