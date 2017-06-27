package com.example.ashutoshtiwari.popularmovies;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ashutosh.tiwari on 27/06/17.
 */

public class RetrofitBuilder {
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    TMDClient tmdClient = retrofit.create(TMDClient.class);
}
