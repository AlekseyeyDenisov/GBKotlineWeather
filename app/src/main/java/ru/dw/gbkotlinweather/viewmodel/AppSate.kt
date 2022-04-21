package ru.dw.gbkotlinweather.viewmodel

import ru.dw.gbkotlinweather.repository.Weather

sealed class AppSate {
    object Loading:AppSate()
    data class Success(val weather: Weather):AppSate()
    data class Error(val error: Throwable):AppSate()
}