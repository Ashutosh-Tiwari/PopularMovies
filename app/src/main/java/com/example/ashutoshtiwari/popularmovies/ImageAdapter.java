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

class ImageAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> imagePath;
    private String baseUrl = "http://image.tmdb.org/t/p/w185/";


    ImageAdapter(Context context, ArrayList<String> imagePath) {
        this.context = context;
        this.imagePath = imagePath;
    }

    @Override
    public int getCount() {
        return imagePath.size();
    }

    @Override
    public Object getItem(int position) {
        return imagePath.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ImageView imageView = (ImageView)convertView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setAdjustViewBounds(true);

        }
        String url = (String) getItem(position);
        Picasso.with(context).load(baseUrl + url).into(imageView);
        return imageView;
    }
}
