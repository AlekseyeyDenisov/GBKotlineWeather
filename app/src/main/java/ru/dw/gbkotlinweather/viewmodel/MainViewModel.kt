package ru.dw.gbkotlinweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.dw.gbkotlinweather.repository.Repository
import ru.dw.gbkotlinweather.repository.RepositoryImpl

class MainViewModel(
    private val liveDate: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl()
) : ViewModel() {
    fun getLiveData(): LiveData<AppState> {
        return liveDate
    }

    fun getDataWeather() {
        liveDate.postValue(AppState.Loading)
        if ((0..6).random() > 3) {
            Thread {
                val answer = repository.getDataServer()
                liveDate.postValue(AppState.Success(answer))

            }.start()
        } else {
            liveDate.postValue(AppState.Error(Throwable("Ошибка")))
        }

    }

}