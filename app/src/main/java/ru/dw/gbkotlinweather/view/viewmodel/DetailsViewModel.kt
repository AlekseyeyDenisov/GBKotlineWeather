package ru.dw.gbkotlinweather.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.dw.gbkotlinweather.model.City
import ru.dw.gbkotlinweather.model.Weather
import ru.dw.gbkotlinweather.repository.api_yandex.OnServerResponseListener
import ru.dw.gbkotlinweather.repository.api_yandex.WeatherLoader


class DetailsViewModel(
    private val liveDate: MutableLiveData<DetailsState> = MutableLiveData()
) : ViewModel() {


    fun getLiveDataCityWeather() = liveDate


    fun upDataWeather(city: City) {
        liveDate.postValue(DetailsState.Loading)
        WeatherLoader(object : OnServerResponseListener {
            override fun onResponseSuccess(weather: Weather) {
                liveDate.postValue(DetailsState.Success(weather))
            }

            override fun error(error: String) {
                liveDate.postValue(DetailsState.Error(Throwable(error)))
            }
        }).getCityWeather(city)
    }

}