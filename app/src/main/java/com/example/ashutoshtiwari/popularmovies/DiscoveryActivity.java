package com.example.ashutoshtiwari.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import java.util.ArrayList;

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
    }

    private void updateUI() {
        images = new ArrayList<>();
        images.add(R.drawable.sample_0);
        images.add(R.drawable.sample_1);
        images.add(R.drawable.sample_2);
        images.add(R.drawable.sample_3);
        images.add(R.drawable.sample_4);
        images.add(R.drawable.sample_5);
        images.add(R.drawable.sample_6);
        images.add(R.drawable.sample_7);
    }
}
