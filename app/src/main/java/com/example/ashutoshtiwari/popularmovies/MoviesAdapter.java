package com.example.ashutoshtiwari.popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ashutosh.tiwari on 21/06/17.
 */

class MoviesAdapter extends BaseAdapter {

    private Context context;
    private String baseUrl = "http://image.tmdb.org/t/p/w185/";
    ArrayList<TheMovieDB> movies;


    MoviesAdapter() {
    }

    MoviesAdapter(Context context, ArrayList<TheMovieDB> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return movies.get(position).getId();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ImageView imageView = (ImageView) convertView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setAdjustViewBounds(true);
        }
        String url = movies.get(position).getPosterPath();
        Picasso.with(context).load(baseUrl + url).into(imageView);
        return imageView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
