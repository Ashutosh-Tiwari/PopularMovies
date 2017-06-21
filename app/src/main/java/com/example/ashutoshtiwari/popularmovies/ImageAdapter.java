package com.example.ashutoshtiwari.popularmovies;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Ashutosh.tiwari on 21/06/17.
 */

class ImageAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Integer> images;

    ImageAdapter(Activity activity, ArrayList<Integer> images) {
        this.activity = activity;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(activity);
            imageView.setPadding(0, 0, 0, 0);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(images.get(position));
        return imageView;
    }
}
