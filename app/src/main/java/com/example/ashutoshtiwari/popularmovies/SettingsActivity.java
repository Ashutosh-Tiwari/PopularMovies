package com.example.ashutoshtiwari.popularmovies;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

/**
 * Created by Ashutosh.tiwari on 23/06/17.
 */

public class SettingsActivity extends Activity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        getFragmentManager()
                .beginTransaction()
                //.replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
