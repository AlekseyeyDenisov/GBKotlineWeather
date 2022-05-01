package ru.dw.gbkotlinweather.utils

import android.content.Context
import android.content.SharedPreferences

private const val CONSTANT_SHARED_PREFERENCES = "weather_sharedPreferences"
private const val SHARED_PREFERENCES_FLOAT_ACTION_BUTTON ="sharedPreferences_floating_action_button"
private const val SHARED_PREFERENCES_IS_INTERNET = "sharedPreferences_floating_is_internet"
private const val SHARED_PREFERENCES_IS_PERMITS_READ_CONTACTS = "permits_read_contact"
private const val SHARED_PREFERENCES_IS_PERMITS_CALL_PHONE = "permits_call_phone"
private const val SHARED_PREFERENCES_IS_PERMITS_LOCATION = "permits_location"

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

    fun setPermitsNumberNotReceivedReadContacts(){
        val count =  getPermitsNumberNotReceivedReadContacts() + 1
        pref.edit().putInt(SHARED_PREFERENCES_IS_PERMITS_READ_CONTACTS,count).apply()
    }
    fun getPermitsNumberNotReceivedReadContacts():Int{
        return pref.getInt(SHARED_PREFERENCES_IS_PERMITS_READ_CONTACTS,0)
    }

    fun setPermitsNumberNotReceivedCallPhone(){
        val count = getPermitsNumberNotReceivedCallPhone() +1
        pref.edit().putInt(SHARED_PREFERENCES_IS_PERMITS_CALL_PHONE,count).apply()
    }
    fun getPermitsNumberNotReceivedCallPhone():Int{
        return pref.getInt(SHARED_PREFERENCES_IS_PERMITS_CALL_PHONE,0)
    }

    fun setPermitsLocation(nextNumber:Int){
        pref.edit().putInt(SHARED_PREFERENCES_IS_PERMITS_LOCATION,nextNumber).apply()
    }
    fun getPermitsLocation():Int{
        return pref.getInt(SHARED_PREFERENCES_IS_PERMITS_LOCATION,0)
    }

}