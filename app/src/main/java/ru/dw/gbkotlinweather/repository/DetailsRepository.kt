package ru.dw.gbkotlinweather.repository

import ru.dw.gbkotlinweather.model.City
import ru.dw.gbkotlinweather.model.Weather
import ru.dw.gbkotlinweather.view.details.DetailsViewModel

interface DetailsRepository {
    fun getWeatherDetails(city: City, callbackDetailsWeather: DetailsViewModel.CallbackDetails)

}
interface HistoryRepository{
    fun getAllWeatherDetails(callbackWeather: DetailsViewModel.CallbackResponseForAll)
    fun addWeather(weather: Weather)
}