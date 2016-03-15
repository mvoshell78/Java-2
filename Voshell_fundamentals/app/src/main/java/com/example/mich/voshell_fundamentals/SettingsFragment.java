package com.example.mich.voshell_fundamentals;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;



/**
 * Created by Mich on 3/10/16.
 */
public class SettingsFragment extends PreferenceFragment {

    public static final String TAG = "SettingsFragment";
    public static MainActivity mainActivity = new MainActivity();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Preference pref = findPreference("DELETE_USER_DATA");
        pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {


                mainActivity.deleteData();

                return true;
            }
        });
    }
}
