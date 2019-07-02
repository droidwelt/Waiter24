package ru.droidwelt.waiter24.main_UI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import ru.droidwelt.waiter24.R;
import ru.droidwelt.waiter24.common.AppCompatActivityMy;
import ru.droidwelt.waiter24.utils.MainUtils;


public class AboutActivity extends AppCompatActivityMy {


    @SuppressWarnings("SpellCheckingInspection")
    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle(new MainUtils().getMainTitle());

        int DisplaySizeX = new MainUtils().getDisplaySizeX();
        int DisplaySizeY = new MainUtils().getDisplaySizeY();
        int DisplayDensityDpi = new MainUtils().getDensityDpi();
        int DisplaySizeXDP = DisplaySizeX * 160 / DisplayDensityDpi;
        int DisplaySizeYDP = DisplaySizeY * 160 / DisplayDensityDpi;
        String s = "";
        s += " OS VIERSION: " + System.getProperty("os.version") + " \n";
        s += " OS INCREMENTAL:" + android.os.Build.VERSION.INCREMENTAL + " \n";
        s += " OS API RELEASE: " + android.os.Build.VERSION.RELEASE + " \n";
        s += " OS API SDK_INT: " + android.os.Build.VERSION.SDK_INT + " \n";
        s += " DEVICE: " + android.os.Build.DEVICE + " \n";
        s += " MODEL: " + android.os.Build.MODEL + " \n";
        s += " PRODUCT: " + android.os.Build.PRODUCT + " \n";
        s += " HARDWARE: " + android.os.Build.HARDWARE + " \n";
        s += " MANUFACTURE: " + android.os.Build.MANUFACTURER + " \n";
        s += " ID: " + android.os.Build.ID + " \n";
        s += " BRAND: " + android.os.Build.BRAND + " \n";
        s += " DISPLAY: " + android.os.Build.DISPLAY + " \n";
        s += " HOST: " + android.os.Build.HOST + " \n";
        s += " SCREEN : " + DisplaySizeX + " x " + DisplaySizeY;
        s += " /  " + DisplaySizeXDP+ " x " + DisplaySizeYDP + " DP";
        s += " / " + DisplayDensityDpi + " DPI";
        s += " / " + String.format("%.2f", new MainUtils().getDisplayFisSize()) + " inch";

        TextView tv_about3 = findViewById(R.id.tv_about3);
        tv_about3.setText(s);
    }



}
