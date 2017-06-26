package com.example.ashutoshtiwari.popularmovies;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ashutosh.tiwari on 23/06/17.
 */

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Fragment fragment = new SettingsFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment, "settings_fragment");
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
