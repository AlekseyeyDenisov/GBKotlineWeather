package ru.dw.gbkotlinweather.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import ru.dw.gbkotlinweather.R

class BroadcastReceiverWeather : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action){
            ConnectivityManager.CONNECTIVITY_ACTION ->{
                Toast.makeText(context, context?.getString(R.string.message_connection), Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }
}