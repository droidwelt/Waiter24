package ru.droidwelt.waiter24.utils;

import android.annotation.SuppressLint;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import ru.droidwelt.waiter24.R;
import ru.droidwelt.waiter24.common.Appl;


public class MainUtils {


    public static String dateToString(Date dt) {
        if (dt != null) {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            return dateFormat.format(dt);
        } else {
            return "";
        }
    }

    public static String floatToStringEx(float v) {
        if (v == 0) {
            return "";
        } else {
            return  "€ " + Appl.fmtM.format(v);
        }
    }


    public double getDisplayFisSize() {
        return Math.hypot((double) getDisplaySizeX(), (double) getDisplaySizeY()) / getDensityDpi();
    }

    public int getDisplaySizeX() {
        return Appl.getContext().getResources().getDisplayMetrics().widthPixels;
    }

    public int getDisplaySizeY() {
        return Appl.getContext().getResources().getDisplayMetrics().heightPixels;
    }

    public int getDensityDpi() {
        return Appl.getContext().getResources().getDisplayMetrics().densityDpi;
    }


    public static String strnormalize(String s) {
        if (s == null)
            return "";
        else
            return s;
    }

    public static String intToStrEx(int i) {
        if (i == 0)
            return "0";
        else
            return Integer.toString(i);
    }



  public  int getVersionCode() {
        PackageInfo pinfo;
        try {
            pinfo = Appl.getContext().getPackageManager().getPackageInfo(Appl.getContext().getPackageName(), 0);
            return  pinfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

    public  String getVersionName() {
        PackageInfo pinfo;
        try {
            pinfo = Appl.getContext().getPackageManager().getPackageInfo(Appl.getContext().getPackageName(), 0);
            return pinfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }


    public String getMainTitle() {
        PackageInfo pinfo;
        try {
            pinfo = Appl.getContext().getPackageManager().getPackageInfo(Appl.getContext().getPackageName(), 0);
            return Appl.getContext().getString(R.string.app_name) + "  " + pinfo.versionName + "." + pinfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return Appl.getContext().getString(R.string.app_name);
        }
    }


}
