package ru.dw.gbkotlinweather.view.weatherlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.dw.gbkotlinweather.repository.Repository
import ru.dw.gbkotlinweather.repository.RepositoryImpl
import ru.dw.gbkotlinweather.view.AppState

class CityViewModel(
    private val liveDate: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl()
) : ViewModel() {
    fun getLiveData(): LiveData<AppState> {
        return liveDate
    }

    fun getDataWeatherRussia() = getDataWeather(true)
    fun getDataWeatherWorld() = getDataWeather(false)

    private fun getDataWeather(isRussian: Boolean) {
        liveDate.postValue(AppState.Loading)
        Thread {
            val answer =
                if (isRussian) repository.getRussianWeatherFromLocalStorage() else repository.getWorldWeatherFromLocalStorage()
            liveDate.postValue(AppState.Success(answer))

        }.start()
    }
}