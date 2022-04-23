package ru.dw.gbkotlinweather.view

import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ru.dw.gbkotlinweather.R
import ru.dw.gbkotlinweather.view.weatherlist.CityListFragment

class MainActivity : AppCompatActivity() {

    private val receiverWeather by lazy {
        initBroadcastRegister()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CityListFragment.newInstance())
                .commit()
        }
    }

    private fun initBroadcastRegister(): BroadcastReceiver {
        return BroadcastReceiverWeather()
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(receiverWeather, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
    }


    override fun onPause() {
        super.onPause()
        try {
            unregisterReceiver(receiverWeather)
        } catch (e: IllegalArgumentException) {
            Log.d("@@@", "not registered: ${e}")
        }
    }


}