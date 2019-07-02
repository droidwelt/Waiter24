package ru.droidwelt.waiter24.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import ru.droidwelt.waiter24.R;
import ru.droidwelt.waiter24.common.Appl;
import ru.droidwelt.waiter24.common.Const;


public class PrefUtils {

    public boolean getNetworkMobile() {
        String skey = "network_mobile";
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Appl.getContext());
        return sp.getBoolean(skey, false);
    }

    public boolean getNetworkWiFi() {
        String skey = "network_wifi";
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Appl.getContext());
        return sp.getBoolean(skey, false);
    }

    public String getWaiterCode() {
        String skey = "waiter_code";
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Appl.getContext());
        return sp.getString(skey, "??");
    }

    public String getWaiterName() {
        String skey = "waiter_name";
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Appl.getContext());
        return sp.getString(skey, "??");
    }

    public void setWaiterName(String v) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Appl.getContext());
        String skey = "waiter_name";
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(skey, v);
        editor.apply();
    }


    public boolean getUseVibrate() {
        String skey = "use_vibrate";
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Appl.getContext());
        return sp.getBoolean(skey, false);
    }


    public String getServerName() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Appl.getContext());
        String key = "server_name";
        String s = Appl.getContext().getString(R.string.s_pref_server_addr_not_def);
        return sp.getString(key, s);
    }


    public static String getRootURL() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Appl.getContext());
         return "http://"
                 + sp.getString("server_name", "http://192.168.1.2")
                 + Const.PHPName;
    }




}
