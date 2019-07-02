package ru.droidwelt.waiter24.main_UI;


import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.View;
import android.widget.ImageButton;

import ru.droidwelt.waiter24.R;
import ru.droidwelt.waiter24.common.AppCompatActivityMy;


public class PrefActivity extends AppCompatActivityMy {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref);
        ImageButton ib_back = findViewById(R.id.ib_back);
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        createFragment();
    }

    private void createFragment() {
        PreferenceFragment pr = new PrefFragment();
        getFragmentManager().beginTransaction().replace(R.id.ly_fr, pr).commit();
    }

}
