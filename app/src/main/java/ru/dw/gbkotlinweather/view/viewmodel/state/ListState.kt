package ru.dw.gbkotlinweather.view.viewmodel.state

import ru.dw.gbkotlinweather.model.Weather

sealed class ListState {
    object Loading : ListState()
    data class Success(val weatherList:List<Weather>): ListState()
    data class Error(val error:Throwable): ListState()
}