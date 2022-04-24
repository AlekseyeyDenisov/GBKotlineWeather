package ru.dw.gbkotlinweather.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import ru.dw.gbkotlinweather.utils.SharedPreferencesManager

class BroadcastReceiverWeather : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val pref = SharedPreferencesManager(context)
        when (intent.action) {
            "android.net.conn.CONNECTIVITY_CHANGE" -> {
                val isConnectivity =
                    intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)
                pref.setIsInternet(!isConnectivity)
            }
        }
    }


}
