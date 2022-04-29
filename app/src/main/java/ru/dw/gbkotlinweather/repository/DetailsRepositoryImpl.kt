package ru.dw.gbkotlinweather.repository

import android.os.Handler
import android.os.Looper
import ru.dw.gbkotlinweather.MyApp
import ru.dw.gbkotlinweather.data.api_yandex.retrofit.HelperRetrofitDetails
import ru.dw.gbkotlinweather.model.City
import ru.dw.gbkotlinweather.model.Weather
import ru.dw.gbkotlinweather.view.details.DetailsViewModel

class DetailsRepositoryImpl : DetailsRepository, HistoryRepository {
    private val helperRoom = MyApp.getDBRoom()
    private val helperRetrofit = HelperRetrofitDetails


    override fun getAllWeatherDetails(callbackWeather: DetailsViewModel.CallbackResponseForAll) {
        Thread {
            helperRoom.getAllWeatherDetails(object : DetailsViewModel.CallbackResponseForAll {
                override fun onResponseSuccess(weather: List<Weather>) {
                    Handler(Looper.getMainLooper()).post {
                        callbackWeather.onResponseSuccess(weather)
                    }
                }

                override fun onFail(error: String) {
                    Handler(Looper.getMainLooper()).post {
                        callbackWeather.onFail(error)
                    }
                }

            })

        }.start()

    }

    override fun getWeatherDetails(
        city: City,
        callbackDetailsWeather: DetailsViewModel.CallbackDetails
    ) {

        Thread {
            val helper = if (isInternet()) helperRetrofit else helperRoom

            helper.getWeatherDetails(city, object : DetailsViewModel.CallbackDetails {
                override fun onResponseSuccess(weather: Weather) {
                    Handler(Looper.getMainLooper()).post {
                        callbackDetailsWeather.onResponseSuccess(weather)
                    }
                    if (isInternet()) addWeather(weather)
                }

                override fun onFail(error: String) {
                    callbackDetailsWeather.onFail(error)
                }

            })


        }.start()
    }

    override fun addWeather(weather: Weather) {
        helperRoom.addWeather(weather)
    }

    private fun isInternet() = MyApp.pref.getIsInternet()


}