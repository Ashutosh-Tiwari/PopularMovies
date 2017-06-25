package com.example.ashutoshtiwari.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String MAX_RATING = "10";
    TheMovieDB theMovieDB;
    private ImageView imageViewMoviePoster;
    private TextView textViewTitle;
    private TextView textViewReleaseYear;
    private TextView textViewUserRating;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Context context = getApplicationContext();
        String baseUrl = "http://image.tmdb.org/t/p/w185/";
        Intent intent = getIntent();
        //int position = intent.getIntExtra("item_position", 0);
        theMovieDB = getIntent().getExtras().getParcelable("movie");

        String[] releaseDate = theMovieDB.getReleaseDate().split("-", 2);

        textViewTitle = (TextView) findViewById(R.id.textview_movie_title);
        textViewReleaseYear = (TextView) findViewById(R.id.textview_release_year);
        textViewUserRating = (TextView) findViewById(R.id.textview_user_rating);
        imageViewMoviePoster = (ImageView) findViewById(R.id.imageview_movie_poster);

        textViewTitle.setText(theMovieDB.getTitle());
        imagePath = theMovieDB.getPosterPath();
        textViewReleaseYear.setText(releaseDate[0]);
        textViewUserRating.setText(String.valueOf(theMovieDB.getVoteAverage()) + "/" + MAX_RATING);

        Picasso.with(context).load(baseUrl + imagePath).into(imageViewMoviePoster);
    }
}
