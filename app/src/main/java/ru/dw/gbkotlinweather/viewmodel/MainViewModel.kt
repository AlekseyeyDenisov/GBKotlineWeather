package ru.dw.gbkotlinweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.dw.gbkotlinweather.repository.Repository
import ru.dw.gbkotlinweather.repository.RepositoryIpl

class MainViewModel(
    private val liveDataWeather: MutableLiveData<AppSate> = MutableLiveData(),
    private val repository: Repository = RepositoryIpl()
) : ViewModel() {

    fun getLiveDataWeather(): LiveData<AppSate> {
        return liveDataWeather
    }

    fun getWeather() {
        liveDataWeather.postValue(AppSate.Loading)
        Thread {
            if ((0..6).random() > 3) {
                val data = repository.getDataWeatherServer()
                liveDataWeather.postValue(AppSate.Success(data))
            } else {
                liveDataWeather.postValue(AppSate.Error(Throwable("Ошибка")))
            }
        }.start()
    }

}