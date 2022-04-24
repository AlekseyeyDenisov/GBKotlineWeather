package ru.dw.gbkotlinweather.repository

import ru.dw.gbkotlinweather.model.City
import ru.dw.gbkotlinweather.view.viewmodel.DetailsViewModel

fun interface DetailsRepository {
    fun getWeatherDetails(city: City, callbackDetailsWeather: DetailsViewModel.CallbackDetails)
}