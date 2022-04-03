package ru.dw.gbkotlinweather.repository

import ru.dw.gbkotlinweather.view.AppState

class RepositoryImpl : Repository {
    private val data:Repository = DataLocal()

    override fun getWorldWeatherFromLocalStorage(): AppState = data.getWorldWeatherFromLocalStorage()
    override fun getRussianWeatherFromLocalStorage():AppState = data.getRussianWeatherFromLocalStorage()

}