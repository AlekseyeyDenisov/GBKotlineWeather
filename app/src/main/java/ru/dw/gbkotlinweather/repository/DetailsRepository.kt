package ru.dw.gbkotlinweather.repository

import ru.dw.gbkotlinweather.model.City
import ru.dw.gbkotlinweather.repository.api_yandex.OnServerResponseListener

fun interface DetailsRepository {
    fun getWeatherDetails(city: City,callbackWeather: OnServerResponseListener)
}