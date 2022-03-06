package com.sample.billeasy.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import org.greenrobot.eventbus.EventBus


class NetworkConnectivity : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {

        val connMgr = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val wifi = connMgr
            .getNetworkInfo(ConnectivityManager.TYPE_WIFI)

        val mobile = connMgr
            .getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        Log.e("is connected","yelkm,s")
        if (wifi!!.isConnected || mobile!!.isConnected) {
            Log.e("is connected","yes")
            EventBus.getDefault().post("Connected");
        }
        else{
            Log.e("is connected","no")

            EventBus.getDefault().post("Not Connected");
        }

    }
}