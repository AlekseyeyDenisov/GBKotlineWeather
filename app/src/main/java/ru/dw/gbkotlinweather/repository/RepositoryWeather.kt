package ru.dw.gbkotlinweather.repository


import ru.dw.gbkotlinweather.view.viewmodel.ResponseState

interface RepositoryWeather {
    fun getCityWeather(responseState: ResponseState)
}