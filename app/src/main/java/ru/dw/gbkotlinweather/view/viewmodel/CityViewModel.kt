package ru.dw.gbkotlinweather.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.dw.gbkotlinweather.repository.RepositoryImpl
import ru.dw.gbkotlinweather.view.viewmodel.state.ListState

class CityViewModel(
    private val liveDate: MutableLiveData<ListState> = MutableLiveData(),
    private val repository: RepositoryImpl = RepositoryImpl()
) : ViewModel() {
    fun getLiveData(): LiveData<ListState> {
        return liveDate
    }

    fun getDataListCity(flag:Boolean) = getDataWeather(flag)


    private fun getDataWeather(isRussian: Boolean) {
        liveDate.postValue(ListState.Loading)
        val answer =
            if (isRussian) {
                repository.getRussianWeatherFromLocalStorage()
            } else {
                repository.getWorldWeatherFromLocalStorage()
            }
        liveDate.postValue(answer)

    }

}