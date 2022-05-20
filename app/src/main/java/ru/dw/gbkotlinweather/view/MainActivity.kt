package ru.dw.gbkotlinweather.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import ru.dw.gbkotlinweather.MyApp
import ru.dw.gbkotlinweather.R
import ru.dw.gbkotlinweather.data.firebase.ServiceFirebaseWeather
import ru.dw.gbkotlinweather.utils.*
import ru.dw.gbkotlinweather.view.contacts.ContactFragment
import ru.dw.gbkotlinweather.view.googlemap.MapsFragment
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
        tokenFCM()
        //push("MainActivity", "message")
    }
    private fun push(title: String, message: String) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(applicationContext, MainActivity::class.java)
        val contentIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilderLower = NotificationCompat.Builder(this,
            ServiceFirebaseWeather.CHANNEL_ID_LOW
        ).apply {
            setSmallIcon(R.drawable.ic_rocket)
            setContentTitle(title)
            setContentText(message)
            setContentIntent(contentIntent)

            priority = NotificationManager.IMPORTANCE_LOW
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelNameLow = "Name ${ServiceFirebaseWeather.CHANNEL_ID_LOW}"
            val channelDescriptionLow = "Description ${ServiceFirebaseWeather.CHANNEL_ID_LOW}"
            val channelPriorityLow = NotificationManager.IMPORTANCE_LOW
            val channelLow =
                NotificationChannel(ServiceFirebaseWeather.CHANNEL_ID_LOW, channelNameLow, channelPriorityLow).apply {
                    description = channelDescriptionLow
                }
            notificationManager.createNotificationChannel(channelLow)
        }

        notificationManager.notify(ServiceFirebaseWeather.NOTIFICATION_ID_LOW, notificationBuilderLower.build())
    }

    private fun tokenFCM() {
        if (TOKEN_DEFAULT == MyApp.pref.getTokenFCM()){
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.d(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
                val token = task.result
                Log.d(TAG , token)
                MyApp.pref.setTokenFCM(token)
            })
        }
        else  Log.d(TAG, "token FCM: ${MyApp.pref.getTokenFCM()}")
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

            R.id.action_user_contact ->{
                val fragmentContact = supportFragmentManager.findFragmentByTag(TAG_FRAGMENT_CONTACT)
                if (fragmentContact == null){
                    supportFragmentManager.apply {
                        beginTransaction()
                            .replace(R.id.container, ContactFragment.newInstance(),
                                TAG_FRAGMENT_CONTACT)
                            .addToBackStack("")
                            .commit()
                    }
                }
            }
            R.id.action_map ->{
                val fragmentMap = supportFragmentManager.findFragmentByTag(TAG_FRAGMENT_MAP)
                if (fragmentMap == null){
                    supportFragmentManager.apply {
                        beginTransaction()
                            .replace(R.id.container,MapsFragment(),
                                TAG_FRAGMENT_MAP)
                            .addToBackStack("")
                            .commit()
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


}