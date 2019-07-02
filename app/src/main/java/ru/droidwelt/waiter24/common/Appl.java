package ru.droidwelt.waiter24.common;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import ru.droidwelt.waiter24.utils.ConnectionStateMonitor;
import ru.droidwelt.waiter24.utils.SoundUtils;

public class Appl extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static String ORD_TABLENO = "";
    public static int ORD_NOMER = 0;
    public static int ORD_STATE = -1;
    public static int ORD_PAYMENT = -1;
    public static int ORD_POS = -1;
    public static int POS_POS = -1;
    public static int POS_STATE = 0;
    public static String POS_ID = "";
    public static String ORD_ID = "";

    public static boolean ORDERSMODE = true;

    // состояние подачи текущего заказа
    // 0 - пустой
    // 1 - редактирование
    // 2 - заказ передается
    // 3 - заказ передан
    // 4 - исполнение начато
    // 5 - исполнение завершено
    // 6 - выдан
    // 7* - счет запрашивается
    // 8* - счет запрошен
    // 9* - счет оплачен
    //   public static int ORDER_STATE = 0;

    // состояние оплаты текущего заказа
    // 0 -
    // 1* -
    // 2 - вы можете заказать счёт
    // 3* - вы можете заказать счёт
    // 4* - вы можете заказать счёт
    // 5* - вы можете заказать счёт
    // 6* - вы можете заказать счёт
    // 7 - счет запрашивается
    // 8 - счет запрошен
    // 9 - счет оплачен
    //   public static int PAYMENT_STATE = 0;


    //   private static String WAITER = "??";

    final public static NumberFormat fmtN = new DecimalFormat("#");
    final public static NumberFormat fmtM = new DecimalFormat("#.#");

    public static ConnectivityManager connectivityManager;


    @SuppressLint("StaticFieldLeak")
    private static Appl instanse;

    public static boolean isActiveConnection() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return (activeNetworkInfo != null);
    }


    public static void setCsmOn() {
        csm.enable(csmRegistred);
        csmRegistred = true;
    }

    public static void setCsmOff() {
        csm.disable(csmRegistred);
        csmRegistred = false;
    }

    private static Boolean csmRegistred = false;
    private static ConnectionStateMonitor csm;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        instanse = this;

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

      /*  int[] capabilities = new int[]{ NetworkCapabilities.NET_CAPABILITY_INTERNET };
        int[] transportTypes = new int[]{ NetworkCapabilities.TRANSPORT_CELLULAR };
        alwaysPreferNetworksWith(capabilities, transportTypes); */

        isActiveConnection();

        SoundUtils.initSound();

        csm = new ConnectionStateMonitor(context);

    }




    private static Appl geInstanse() {
        return instanse;
    }

    public static Context getContext() {
        return context;
    }




    private void alwaysPreferNetworksWith(@NonNull int[] capabilities, @NonNull int[] transportTypes) {
        NetworkRequest.Builder request = new NetworkRequest.Builder();
        for (int cap: capabilities) {
            request.addCapability(cap);
        }
        for (int trans: transportTypes) {
            request.addTransportType(trans);
        }
        connectivityManager.registerNetworkCallback(request.build(), new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                try {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                        ConnectivityManager.setProcessDefaultNetwork(network);
                        Log.e("APPL", "ConnectivityManager.NetworkCallback.setProcessDefaultNetwork: ");
                    } else {
                        connectivityManager.bindProcessToNetwork(network);
                        Log.e("APPL", "ConnectivityManager.NetworkCallback.bindProcessToNetwork: ");
                    }
                } catch (IllegalStateException e) {
                    Log.e("APPL", "ConnectivityManager.NetworkCallback.onAvailable: ", e);
                }
            }
        });
    }




}
