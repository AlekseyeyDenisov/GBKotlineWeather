package ru.dw.gbkotlinweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.dw.gbkotlinweather.repository.RepositoryImpl

class MainViewModel(
    private val livedata: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: RepositoryImpl = RepositoryImpl()
) : ViewModel() {
    fun getLiveData(): LiveData<AppState> {
        return livedata
    }

    fun getWeather() {
        livedata.postValue(AppState.Loading)
        Thread{
            if ((0..6).random() > 3){
                val answer = repository.getWeatherFromServer()
                livedata.postValue(AppState.Success(answer))
            }else{
                livedata.postValue(AppState.Error(Throwable("Ошибка")))
            }

        }.start()

    }


}