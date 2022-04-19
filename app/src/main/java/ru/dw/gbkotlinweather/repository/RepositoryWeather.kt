package ru.dw.gbkotlinweather.repository


import ru.dw.gbkotlinweather.model.City
import ru.dw.gbkotlinweather.repository.api_yandex.OnServerResponseListener
import ru.dw.gbkotlinweather.model.Weather

interface RepositoryWeather {
    fun getCityWeather(city: City,callback: OnServerResponseListener)
}