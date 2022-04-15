package ru.dw.gbkotlinweather.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar
import ru.dw.gbkotlinweather.BuildConfig

const val YANDEX_DOMAIN = "https://api.weather.yandex.ru/"
const val YANDEX_DOMAIN_HARD_MODE = "http://212.86.114.27/"
const val YANDEX_PATH = "v2/informers?"
const val YANDEX_API_KEY = "X-Yandex-API-Key"
const val API_KEY = BuildConfig.WEATHER_API_KEY
const val CONNECTION_TIMEOUT = 1000
const val READ_TIMEOUT = 1000


fun View.showSnackBar(
    text:String,
    length:Int = Snackbar.LENGTH_INDEFINITE
){
    Snackbar.make( this,text,length ).show()
}

