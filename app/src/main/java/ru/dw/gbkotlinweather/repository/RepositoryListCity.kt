package ru.dw.gbkotlinweather.repository

import ru.dw.gbkotlinweather.view.viewmodel.AppState

interface RepositoryListCity {

    fun getWorldWeatherFromLocalStorage(): AppState
    fun getRussianWeatherFromLocalStorage(): AppState

}