package ru.dw.gbkotlinweather.repository

import android.os.Handler
import android.os.Looper
import ru.dw.gbkotlinweather.MyApp
import ru.dw.gbkotlinweather.model.City
import ru.dw.gbkotlinweather.model.Weather
import ru.dw.gbkotlinweather.utils.convertHistoryEntityToWeather
import ru.dw.gbkotlinweather.utils.convertListHistoryEntityToWeather
import ru.dw.gbkotlinweather.utils.convertWeatherToEntity
import ru.dw.gbkotlinweather.view.viewmodel.DetailsViewModel

class DetailsRepositoryRoomImpl : DetailsRepository, DetailsRepositoryAll, DetailsRepositoryAdd {

    override fun getAllWeatherDetails(callback: DetailsViewModel.CallbackResponseForAll) {
        Thread {
            val result = MyApp.getDBRoom().getAll()
            Handler(Looper.getMainLooper()).post {
                callback.onResponseSuccess(convertListHistoryEntityToWeather(result))
            }
        }.start()

    }

    override fun getWeatherDetails(
        city: City,
        callbackDetailsWeather: DetailsViewModel.CallbackDetails
    ) {
        Thread {
            val result = MyApp.getDBRoom().getHistoryForCity(city.name)
            if (result != null) {
                Handler(Looper.getMainLooper()).post {
                    callbackDetailsWeather.onResponseSuccess(convertHistoryEntityToWeather(result))
                }
            } else {
                callbackDetailsWeather.onFail("В истории нет записей")
            }

        }.start()
    }

    override fun addWeather(weather: Weather, callbackDetails: DetailsViewModel.CallbackDetails) {
        MyApp.getDBRoom().insert(convertWeatherToEntity(weather))
    }


}