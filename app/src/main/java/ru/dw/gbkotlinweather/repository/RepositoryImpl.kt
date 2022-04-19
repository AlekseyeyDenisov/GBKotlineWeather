package ru.dw.gbkotlinweather.repository

import ru.dw.gbkotlinweather.view.viewmodel.AppState

class RepositoryImpl : RepositoryListCity {
    private val data:RepositoryListCity = DataLocal()


    override fun getWorldWeatherFromLocalStorage(): AppState = data.getWorldWeatherFromLocalStorage()

    override fun getRussianWeatherFromLocalStorage(): AppState = data.getRussianWeatherFromLocalStorage()




}