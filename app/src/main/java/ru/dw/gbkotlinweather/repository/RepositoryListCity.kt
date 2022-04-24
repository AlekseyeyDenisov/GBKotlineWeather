package ru.dw.gbkotlinweather.repository

import ru.dw.gbkotlinweather.view.viewmodel.state.ListState

interface RepositoryListCity {

    fun getWorldWeatherFromLocalStorage(): ListState
    fun getRussianWeatherFromLocalStorage(): ListState

}