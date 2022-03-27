package ru.dw.gbkotlinweather.viewmodel

import ru.dw.gbkotlinweather.repository.Weather

sealed class AppState {
    object Loading:AppState()
    data class Success(val weatherData: Weather):AppState()
    data class Error(val error:Throwable):AppState()
}