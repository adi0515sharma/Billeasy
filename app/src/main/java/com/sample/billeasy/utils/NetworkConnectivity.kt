package com.sample.billeasy.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager


class NetworkConnectivity : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {

        val connMgr = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val wifi = connMgr
            .getNetworkInfo(ConnectivityManager.TYPE_WIFI)

        val mobile = connMgr
            .getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

        if (wifi!!.isConnected || mobile!!.isConnected) {
            // do stuff
        }
        else{

        }

    }
}