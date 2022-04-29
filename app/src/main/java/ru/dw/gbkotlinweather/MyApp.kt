package ru.dw.gbkotlinweather

import android.app.Application
import androidx.room.Room
import ru.dw.gbkotlinweather.data.room.HelperRoom
import ru.dw.gbkotlinweather.data.room.MyDB
import ru.dw.gbkotlinweather.utils.SharedPreferencesManager


class MyApp : Application() {


    companion object {
        private var appContext: MyApp? = null
        lateinit var pref: SharedPreferencesManager

        private var dbRoom: MyDB? = null

        fun getDBRoom(): HelperRoom {
            if (dbRoom == null) {
                if (appContext != null) {
                    dbRoom = Room.databaseBuilder(appContext!!, MyDB::class.java, "test").build()
                } else {
                    throw IllegalStateException("Пустой  appContext в APP")
                }
            }

            return HelperRoom(dbRoom!!.historyDao())
        }
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        pref = SharedPreferencesManager(this)


    }


}