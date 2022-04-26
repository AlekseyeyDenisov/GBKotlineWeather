package ru.dw.gbkotlinweather.utils

import android.content.Context
import android.content.SharedPreferences

private const val CONSTANT_SHARED_PREFERENCES = "weather_sharedPreferences"
private const val SHARED_PREFERENCES_FLOAT_ACTION_BUTTON ="sharedPreferences_floating_action_button"
private const val SHARED_PREFERENCES_IS_INTERNET = "sharedPreferences_floating_is_internet"

class SharedPreferencesManager(context: Context) {
    private var pref: SharedPreferences =
        context.getSharedPreferences(CONSTANT_SHARED_PREFERENCES, Context.MODE_PRIVATE)


    fun getDefaultFloatingActionButton(): Boolean {
        return pref.getBoolean(SHARED_PREFERENCES_FLOAT_ACTION_BUTTON, true)
    }

    fun setDefaultFloatingActionButton(flag: Boolean) {
        pref.edit().putBoolean(SHARED_PREFERENCES_FLOAT_ACTION_BUTTON, flag).apply()
    }

    fun getIsInternet(): Boolean {
        return pref.getBoolean(SHARED_PREFERENCES_IS_INTERNET, true)
    }

    fun setIsInternet(flag: Boolean) {
        pref.edit().putBoolean(SHARED_PREFERENCES_IS_INTERNET, flag).apply()
    }

}