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
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
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
        push("MainActivity", "message")
        //val args = intent.getStringExtra(INTENT_EXTRA_FRAGMENT)


    }

    private fun push(titleNotify: String, messageNotify: String) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationView = RemoteViews(applicationContext.packageName,R.layout.notification_layout)

        val notificationBuilderLower = NotificationCompat.Builder(
            this,
            ServiceFirebaseWeather.CHANNEL_ID_LOW
        ).apply {
            setSmallIcon(R.drawable.ic_rocket)
            setContentTitle(titleNotify)
            setContentText(messageNotify)
            //setContentIntent(pendingIntent())
            setCustomContentView(notificationView)

            priority = NotificationManager.IMPORTANCE_LOW
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelNameLow = "Name ${ServiceFirebaseWeather.CHANNEL_ID_LOW}"
            val channelDescriptionLow = "Description ${ServiceFirebaseWeather.CHANNEL_ID_LOW}"
            val channelPriorityLow = NotificationManager.IMPORTANCE_LOW
            val channelLow =
                NotificationChannel(
                    ServiceFirebaseWeather.CHANNEL_ID_LOW,
                    channelNameLow,
                    channelPriorityLow
                ).apply {
                    description = channelDescriptionLow
                }
            notificationManager.createNotificationChannel(channelLow)
        }


        notificationView.setOnClickPendingIntent(
            R.id.openMapFragment,
            pendingIntent(EXTRA_NAME_MAP, 33)
        )
        notificationView.setOnClickPendingIntent(
            R.id.openHistoryFragment,
            pendingIntent(EXTRA_NAME_HISTORY, 45)
        )

        notificationManager.notify(
            ServiceFirebaseWeather.NOTIFICATION_ID_LOW,
            notificationBuilderLower.build()
        )
    }

    private fun intentExtra() {
        val args = intent.getStringExtra(INTENT_EXTRA_FRAGMENT)
        Log.d(TAG, "onCreate getStringExtra: " + intent.getStringExtra(INTENT_EXTRA_FRAGMENT))
        when (args) {
            EXTRA_NAME_MAP -> {
                if (supportFragmentManager.findFragmentByTag(TAG_FRAGMENT_MAP) == null) {
                    goToFragment(MapsFragment(), TAG_FRAGMENT_MAP)
                }

            }
            EXTRA_NAME_HISTORY->{
                if (supportFragmentManager.findFragmentByTag(TAG_FRAGMENT_HISTORY) == null) {
                    goToFragment(CityHistoryListFragment.newInstance(), TAG_FRAGMENT_HISTORY)

                }
            }
        }
        intent.removeExtra(INTENT_EXTRA_FRAGMENT)

    }

    private fun pendingIntent(nameFragment: String, requestCode: Int): PendingIntent? {
        val notifyIntent = Intent(this, MainActivity::class.java)
        notifyIntent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP
                or Intent.FLAG_ACTIVITY_SINGLE_TOP)

        notifyIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        notifyIntent.putExtra(INTENT_EXTRA_FRAGMENT, nameFragment)
        return PendingIntent.getActivity(this, requestCode, notifyIntent, 0)
    }

    private fun tokenFCM() {
        if (TOKEN_DEFAULT == MyApp.pref.getTokenFCM()) {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.d(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
                val token = task.result
                Log.d(TAG, token)
                MyApp.pref.setTokenFCM(token)
            })
        } else Log.d(TAG, "token FCM: ${MyApp.pref.getTokenFCM()}")
    }

    private fun initBroadcastRegister(): BroadcastReceiver {
        return BroadcastReceiverWeather()
    }

    override fun onResume() {
        super.onResume()
        intentExtra()
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
                if (fragmentHistory == null) {
                    goToFragment(CityHistoryListFragment.newInstance(), TAG_FRAGMENT_HISTORY)

                }
            }
            R.id.action_user_contact -> {
                val fragmentContact = supportFragmentManager.findFragmentByTag(TAG_FRAGMENT_CONTACT)
                if (fragmentContact == null) {
                    goToFragment(ContactFragment.newInstance(), TAG_FRAGMENT_CONTACT)
                }
            }
            R.id.action_map -> {
                val fragmentMap = supportFragmentManager.findFragmentByTag(TAG_FRAGMENT_MAP)
                if (fragmentMap == null) {
                    goToFragment(MapsFragment(), TAG_FRAGMENT_MAP)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goToFragment(fragment: Fragment, tagFragment: String) {
        supportFragmentManager.apply {
            beginTransaction()
                .replace(R.id.container, fragment, tagFragment)
                .addToBackStack("")
                .commit()
        }
    }

}