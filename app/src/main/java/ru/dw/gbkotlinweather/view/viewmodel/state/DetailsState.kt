package ru.dw.gbkotlinweather.view.viewmodel.state

import ru.dw.gbkotlinweather.model.Weather

sealed class DetailsState {
    object Loading : DetailsState()
    data class Success(val weather: Weather) : DetailsState()
    data class Error(val error: Throwable) : DetailsState()
}