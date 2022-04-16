package ru.dw.gbkotlinweather.view.viewmodel

import ru.dw.gbkotlinweather.model.Weather

sealed class AppState {
    object Loading: AppState()
    data class Success(val weatherList:List<Weather>): AppState()
    data class Error(val error:Throwable): AppState()
}