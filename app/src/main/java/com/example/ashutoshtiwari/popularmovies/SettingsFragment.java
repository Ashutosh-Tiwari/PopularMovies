package com.example.ashutoshtiwari.popularmovies;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Ashutosh.tiwari on 23/06/17.
 */

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    Preference preference;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        preference.setOnPreferenceChangeListener(this);
        //bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_sort_key)));
    }

    private void bindPreferenceSummaryToValue(Preference preference) {

     /*   onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));*/
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String stringValue = newValue.toString();

        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);

            if (prefIndex >= 0) {
                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else
            preference.setSummary(stringValue);
        getActivity().setResult(RESULT_OK);

        return true;
    }
}
