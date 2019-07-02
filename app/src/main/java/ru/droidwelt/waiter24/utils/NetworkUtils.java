package ru.droidwelt.waiter24.utils;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import ru.droidwelt.waiter24.R;
import ru.droidwelt.waiter24.common.Appl;

public class NetworkUtils {


    public boolean checkConnection(Boolean showToast) {
        if (Appl.isActiveConnection()) {
          /*  int[] networkTypes = new int[2];
            if (new PrefUtils().getNetworkMobile()) {
                networkTypes[0] = 0;
            } else {
                networkTypes[0] = -1;
            }
            if (new PrefUtils().getNetworkWiFi()) {
                networkTypes[1] = 1;
            } else {
                networkTypes[1] = -1;
            }*/
            int[] networkTypes = {ConnectivityManager.TYPE_MOBILE,ConnectivityManager.TYPE_WIFI};
            try {
               // ConnectivityManager connectivityManager = (ConnectivityManager) Appl.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                for (int networkType : networkTypes) {
                    NetworkInfo activeNetworkInfo = null;
                    if (Appl.connectivityManager != null) {
                        activeNetworkInfo = Appl.connectivityManager.getActiveNetworkInfo();
                    }
                    if (activeNetworkInfo != null && activeNetworkInfo.getType() == networkType)
                        return true;
                }
            } catch (Exception e) {
                return false;
            }
            if (showToast) {
                ToastUtils.DisplayToastError(R.string.s_internet_connection_not_available);
            }
            return false;
        } else {
            return false;
        }
    }


}
