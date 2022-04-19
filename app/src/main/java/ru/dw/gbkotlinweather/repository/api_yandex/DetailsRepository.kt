package ru.dw.gbkotlinweather.repository.api_yandex

import ru.dw.gbkotlinweather.model.City

fun interface DetailsRepository {
    fun getWeatherDetails(city: City,callbackWeather: OnServerResponseListener)
}