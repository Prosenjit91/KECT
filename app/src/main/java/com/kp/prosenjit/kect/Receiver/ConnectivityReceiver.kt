package com.kp.prosenjit.kect.Receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import com.kp.prosenjit.kect.Application.KectApplication

class ConnectivityReceiver : BroadcastReceiver() {
    companion object {
        lateinit var connectivityReceiverListener: ConnectivityReceiverListener
    }

    override fun onReceive(context: Context, arg1: Intent) {
        Log.e("ConnectivityReceiver", "True")
        val cm = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting

        if (connectivityReceiverListener != null) {
            connectivityReceiverListener!!.onNetworkConnectionChanged(isConnected)
        }
    }

    fun isConnected(): Boolean {
        val cm = KectApplication.getInstance().getApplicationContext()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }


    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }



}