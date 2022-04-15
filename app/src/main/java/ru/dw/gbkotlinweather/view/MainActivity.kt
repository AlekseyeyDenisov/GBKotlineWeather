package ru.dw.gbkotlinweather.view

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import ru.dw.gbkotlinweather.R
import ru.dw.gbkotlinweather.service.BroadcastReceiverWeather
import ru.dw.gbkotlinweather.view.weatherlist.CityListFragment

class MainActivity : AppCompatActivity() {
    lateinit var receiver :BroadcastReceiverWeather


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CityListFragment.newInstance())
                .commit()

        }

        receiver = BroadcastReceiverWeather()
        registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver)

    }


}