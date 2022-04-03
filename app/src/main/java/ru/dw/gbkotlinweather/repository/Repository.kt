package ru.dw.gbkotlinweather.repository

import ru.dw.gbkotlinweather.view.AppState

interface Repository {
    fun getWorldWeatherFromLocalStorage():AppState
    fun getRussianWeatherFromLocalStorage():AppState

}