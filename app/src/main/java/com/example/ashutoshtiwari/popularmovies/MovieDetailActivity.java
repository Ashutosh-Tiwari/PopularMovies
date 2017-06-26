package com.example.ashutoshtiwari.popularmovies;

import android.content.Context;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        theMovieDB = getIntent().getExtras().getParcelable("movie");

        textViewTitle = (TextView) findViewById(R.id.textview_movie_title);
        textViewReleaseYear = (TextView) findViewById(R.id.textview_release_year);
        textViewUserRating = (TextView) findViewById(R.id.textview_user_rating);
        imageViewMoviePoster = (ImageView) findViewById(R.id.imageview_movie_poster);

        updateUI();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void updateUI() {
        Context context = getApplicationContext();
        String baseUrl = "http://image.tmdb.org/t/p/w185/";
        String[] releaseDate = theMovieDB.getReleaseDate().split("-", 2);
        String imagePath;

        textViewTitle.setText(theMovieDB.getTitle());
        textViewReleaseYear.setText(releaseDate[0]);
        textViewUserRating.setText(String.valueOf(theMovieDB.getVoteAverage()) + "/" + MAX_RATING);

        imagePath = theMovieDB.getPosterPath();
        Picasso.with(context).load(baseUrl + imagePath).into(imageViewMoviePoster);
    }
}
