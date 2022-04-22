package ru.dw.gbkotlinweather

import android.app.Application
import ru.dw.gbkotlinweather.utils.SharedPreferencesManager


class MyApp:Application() {
    companion object {
        lateinit var sharedPreferencesManager: SharedPreferencesManager
    }

    override fun onCreate() {
        super.onCreate()
        sharedPreferencesManager = SharedPreferencesManager(this)
    }
}