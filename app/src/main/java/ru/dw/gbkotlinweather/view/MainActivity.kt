package ru.dw.gbkotlinweather.view

import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import ru.dw.gbkotlinweather.R
import ru.dw.gbkotlinweather.utils.TAG_FRAGMENT_HISTORY
import ru.dw.gbkotlinweather.view.histiry.CityHistoryListFragment
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


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_history -> {
                val fragmentHistory = supportFragmentManager.findFragmentByTag(TAG_FRAGMENT_HISTORY)
                if (fragmentHistory == null){
                    supportFragmentManager.apply {
                        beginTransaction()
                        .replace(R.id.container, CityHistoryListFragment.newInstance(),TAG_FRAGMENT_HISTORY)
                        .addToBackStack("")
                        .commit()
                    }

                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


}