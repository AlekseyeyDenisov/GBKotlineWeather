package ru.dw.gbkotlinweather

import android.app.Application
import androidx.room.Room
import ru.dw.gbkotlinweather.repository.local.room.HistoryDao
import ru.dw.gbkotlinweather.repository.local.room.MyDB
import ru.dw.gbkotlinweather.utils.SharedPreferencesManager


class MyApp : Application() {


    companion object {
        private var appContext: MyApp? = null

        private var pref: SharedPreferencesManager? = null

        fun getPref(): SharedPreferencesManager {
            if (pref == null) {
                if (appContext != null) {
                    pref = SharedPreferencesManager(appContext!!)
                } else {
                    throw IllegalStateException("Пустой  appContext в APP")
                }
            }
            return pref as SharedPreferencesManager
        }

        private var db: MyDB? = null

         fun getDBRoom():HistoryDao{
             if (db == null){
                 if (appContext != null){
                     db = Room.databaseBuilder(appContext!!, MyDB::class.java, "test")
                         //.allowMainThreadQueries()
                         .build()
                 }else {
                     throw IllegalStateException("Пустой  appContext в APP")
                 }
             }
             return db!!.historyDao()
         }
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this


    }


}