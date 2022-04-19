package ru.dw.gbkotlinweather.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.dw.gbkotlinweather.model.City
import ru.dw.gbkotlinweather.model.Weather
import ru.dw.gbkotlinweather.repository.api_yandex.DetailsRepository
import ru.dw.gbkotlinweather.repository.api_yandex.OnServerResponseListener
import ru.dw.gbkotlinweather.repository.api_yandex.retrofit.DetailsRepositoryRetrofit


class DetailsViewModel(
    private val liveDate: MutableLiveData<DetailsState> = MutableLiveData()
) : ViewModel() {
    //private val repository: DetailsRepository =  WeatherLoader()
    private val repository: DetailsRepository =  DetailsRepositoryRetrofit


    fun getLiveDataCityWeather() = liveDate


    fun upDataWeather(city: City) {
        repository.getWeatherDetails(city,object :OnServerResponseListener{
            override fun onResponseSuccess(weather: Weather) {
                liveDate.postValue(DetailsState.Success(weather))
            }
            override fun error(error: String) {
                liveDate.postValue(DetailsState.Error(Throwable(error)))
            }
        })
        liveDate.postValue(DetailsState.Loading)
    }


}