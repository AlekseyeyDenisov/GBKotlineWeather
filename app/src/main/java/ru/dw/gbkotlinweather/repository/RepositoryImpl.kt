package ru.dw.gbkotlinweather.repository

import ru.dw.gbkotlinweather.model.City
import ru.dw.gbkotlinweather.repository.api_yandex.OnServerResponseListener
import ru.dw.gbkotlinweather.repository.api_yandex.WeatherLoader
import ru.dw.gbkotlinweather.model.Weather
import ru.dw.gbkotlinweather.view.viewmodel.AppState

class RepositoryImpl : RepositoryListCity {
    private val data:RepositoryListCity = DataLocal()


    override fun getWorldWeatherFromLocalStorage(): AppState = data.getWorldWeatherFromLocalStorage()

    override fun getRussianWeatherFromLocalStorage(): AppState = data.getRussianWeatherFromLocalStorage()




}