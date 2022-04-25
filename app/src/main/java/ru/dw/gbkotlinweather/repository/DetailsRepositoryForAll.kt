package ru.dw.gbkotlinweather.repository

import ru.dw.gbkotlinweather.view.details.DetailsViewModel

fun interface DetailsRepositoryAll {
    fun getAllWeatherDetails(callbackWeather: DetailsViewModel.CallbackResponseForAll)
}