package ru.dw.gbkotlinweather.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.dw.gbkotlinweather.repository.RepositoryImpl
import ru.dw.gbkotlinweather.repository.RepositoryWeather
import ru.dw.gbkotlinweather.model.Weather


class DetailsViewModel(
    private val liveDate: MutableLiveData<ResponseState> = MutableLiveData()
) : ViewModel() {

    private val repository: RepositoryWeather by lazy {
        RepositoryImpl()
    }

    fun getLiveDataCityWeather() = liveDate


    fun upDataWeather(weather: Weather) {
        repository.getCityWeather(weather){
            liveDate.postValue(it)
        }
    }

}