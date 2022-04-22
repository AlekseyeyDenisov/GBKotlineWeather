package ru.dw.gbkotlinweather.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast

class BroadcastReceiverWeather : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            ConnectivityManager.CONNECTIVITY_ACTION -> {
                val noConnectivity =
                    intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)
                if (!noConnectivity) {
                    onConnectionFound(context)
                } else {
                    onConnectionLost(context)
                }
            }
        }
    }

    private fun onConnectionLost(context: Context) {
        Toast.makeText(context, "Connection lost", Toast.LENGTH_LONG).show()
    }

    private fun onConnectionFound(context: Context) {
        Toast.makeText(context, "Connection", Toast.LENGTH_LONG).show()
    }


}
