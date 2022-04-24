package ru.dw.gbkotlinweather.repository

import ru.dw.gbkotlinweather.repository.local.DataLocalListCity
import ru.dw.gbkotlinweather.view.viewmodel.state.ListState

class RepositoryImpl : RepositoryListCity {
    private val data:RepositoryListCity = DataLocalListCity()


    override fun getWorldWeatherFromLocalStorage(): ListState = data.getWorldWeatherFromLocalStorage()

    override fun getRussianWeatherFromLocalStorage(): ListState = data.getRussianWeatherFromLocalStorage()




}