package com.example.ashutoshtiwari.popularmovies;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by Ashutosh.tiwari on 22/06/17.
 */

public interface TMDClient {
    @GET("3/discover/movie")
    Call<MoviesData> getMovies(@Query("api_key") String key, @Query("page") int page);
}
