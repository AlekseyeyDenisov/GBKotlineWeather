package ru.dw.gbkotlinweather.repository

import ru.dw.gbkotlinweather.view.viewmodel.DetailsViewModel

fun interface DetailsRepositoryAll {
    fun getAllWeatherDetails(callbackWeather: DetailsViewModel.CallbackResponseForAll)
}