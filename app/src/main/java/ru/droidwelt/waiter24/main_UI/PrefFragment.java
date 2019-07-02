package ru.droidwelt.waiter24.main_UI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import ru.droidwelt.waiter24.R;
import ru.droidwelt.waiter24.utils.PrefUtils;


public class PrefFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        prefs.registerOnSharedPreferenceChangeListener(this);

        displayServerName();
    }



    private void displayServerName() {
        EditTextPreference serverName;
        String name = new PrefUtils().getServerName();
        serverName = (EditTextPreference) findPreference("server_name");
        serverName.setSummary(name);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("server_name")) {
            displayServerName();
        }
    }


}
