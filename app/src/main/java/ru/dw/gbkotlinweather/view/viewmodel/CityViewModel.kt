package ru.dw.gbkotlinweather.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.dw.gbkotlinweather.repository.RepositoryImpl

class CityViewModel(
    private val liveDate: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: RepositoryImpl = RepositoryImpl()
) : ViewModel() {
    fun getLiveData(): LiveData<AppState> {
        return liveDate
    }

    fun getDataWeatherRussia() = getDataWeather(true)
    fun getDataWeatherWorld() = getDataWeather(false)

    private fun getDataWeather(isRussian: Boolean) {
        liveDate.postValue(AppState.Loading)
        val answer =
            if (isRussian) {
                repository.getRussianWeatherFromLocalStorage()
            } else {
                repository.getWorldWeatherFromLocalStorage()
            }
        liveDate.postValue(answer)

    }

}