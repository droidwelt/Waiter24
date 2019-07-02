package ru.droidwelt.waiter24.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Handler;

import ru.droidwelt.waiter24.ebus.Events;
import ru.droidwelt.waiter24.ebus.GlobalBus;

public class ConnectionStateMonitor extends ConnectivityManager.NetworkCallback {

    private final NetworkRequest networkRequest;
    private final ConnectivityManager connectivityManager;

    public ConnectionStateMonitor(Context context) {
        networkRequest = new NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR).addTransportType(NetworkCapabilities.TRANSPORT_WIFI).build();
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public void enable(Boolean registred) {
        //   ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (!(registred))
            connectivityManager.registerNetworkCallback(networkRequest, this);
    }

    public void disable(Boolean registred) {
        //  ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (registred)
            connectivityManager.unregisterNetworkCallback(this);
    }


    @Override
    public void onAvailable(Network network) {
        verifyNetWorlState();
    }

    @Override
    public void onUnavailable() {
        verifyNetWorlState();
    }

    @Override
    public void onLost(Network network) {
        verifyNetWorlState();
    }

    private void verifyNetWorlState() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Events.EventsMessage ev = new Events.EventsMessage();
                ev.setMes_code(Events.NETWORK_STATE);
                GlobalBus.getBus().post(ev);
            }
        }, 1000);
    }

   /* private void verifyNetWorlState() {
        Events.EventsMessage ev = new Events.EventsMessage();
        ev.setMes_code(Events.NETWORK_STATE);
        GlobalBus.getBus().post(ev);
    }*/
}