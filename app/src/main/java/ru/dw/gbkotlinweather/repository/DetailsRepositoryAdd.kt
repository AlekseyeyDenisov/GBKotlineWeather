package ru.dw.gbkotlinweather.repository

import ru.dw.gbkotlinweather.model.Weather
import ru.dw.gbkotlinweather.view.details.DetailsViewModel

interface DetailsRepositoryAdd {
    fun addWeather(weather: Weather, callbackDetails: DetailsViewModel.CallbackDetails)
}