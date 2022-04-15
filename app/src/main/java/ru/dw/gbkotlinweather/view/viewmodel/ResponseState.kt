package ru.dw.gbkotlinweather.view.viewmodel


import ru.dw.gbkotlinweather.repository.model.Weather

sealed class ResponseState {
    data class OnResponseSuccess(val weather: Weather): ResponseState()
    data class Error(val error:String): ResponseState()
}