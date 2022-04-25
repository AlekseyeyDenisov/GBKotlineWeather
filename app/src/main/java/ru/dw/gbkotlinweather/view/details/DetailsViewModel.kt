package ru.dw.gbkotlinweather.view.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.dw.gbkotlinweather.model.City
import ru.dw.gbkotlinweather.model.Weather
import ru.dw.gbkotlinweather.repository.DetailsRepository
import ru.dw.gbkotlinweather.repository.DetailsRepositoryImpl
import ru.dw.gbkotlinweather.view.state.DetailsState


class DetailsViewModel(
    private val liveDate: MutableLiveData<DetailsState> = MutableLiveData()
) : ViewModel() {
    //private val repository: DetailsRepository =  WeatherLoader()
    val  repository: DetailsRepository = DetailsRepositoryImpl()


    fun getLiveDataCityWeather() = liveDate


    fun upDataWeather(city: City) {
        repository.getWeatherDetails(city,object : CallbackDetails {
            override fun onResponseSuccess(weather: Weather) {
                liveDate.postValue(DetailsState.Success(weather))
            }
            override fun onFail(error: String) {
                liveDate.postValue(DetailsState.Error(Throwable(error)))
            }
        })
        liveDate.postValue(DetailsState.Loading)
    }

    interface CallbackDetails {
        fun onResponseSuccess(weather: Weather)
        fun onFail(error: String)
    }
    interface CallbackResponseForAll {
        fun onResponseSuccess(weather: List<Weather>)
        fun onFail(error: String)
    }


}