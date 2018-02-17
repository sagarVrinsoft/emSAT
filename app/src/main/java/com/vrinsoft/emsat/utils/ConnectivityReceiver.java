package com.vrinsoft.emsat.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityReceiver extends BroadcastReceiver
{
    private Context context;
    public ConnectivityReceiverListener connectivityReceiverListener;

    public ConnectivityReceiver(Context context, ConnectivityReceiverListener connectivityReceiverListener)
    {
        this.context =  context;
        this.connectivityReceiverListener = connectivityReceiverListener;
    }

    public static IntentFilter getIntentFilterInternetConnectivity()
    {
        return new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
    }
 
    @Override
    public void onReceive(Context context, Intent arg1) {
        if (connectivityReceiverListener != null) {
            connectivityReceiverListener.onNetworkConnectionChanged(isConnected());
        }
    }
 
    public boolean isConnected() {
        ConnectivityManager
                cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }
 
 
    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }
}