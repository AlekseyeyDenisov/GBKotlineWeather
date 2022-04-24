package ru.dw.gbkotlinweather.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.dw.gbkotlinweather.model.Weather
import ru.dw.gbkotlinweather.repository.DetailsRepository
import ru.dw.gbkotlinweather.repository.DetailsRepositoryAll
import ru.dw.gbkotlinweather.repository.DetailsRepositoryRoomImpl
import ru.dw.gbkotlinweather.repository.RepositoryImpl
import ru.dw.gbkotlinweather.view.viewmodel.state.ListState

class HistoryCityViewModel(
    private val liveDate: MutableLiveData<ListState> = MutableLiveData(),
    private val repository: DetailsRepositoryAll = DetailsRepositoryRoomImpl()
) : ViewModel() {
    fun getLiveData(): LiveData<ListState> {
        return liveDate
    }
    init {
        getDataWeather()
    }

    private fun getDataWeather() {
        liveDate.postValue(ListState.Loading)
        repository.getAllWeatherDetails(object:DetailsViewModel.CallbackResponseForAll{
            override fun onResponseSuccess(weather: List<Weather>) {
                val resultSuccess  = ListState.Success(weather)
                liveDate.postValue(resultSuccess)
            }

            override fun onFail(error: String) {
                val resultFail  = ListState.Error(Throwable(error))
                liveDate.postValue(resultFail)
            }

        })
        //liveDate.postValue(answer)

    }

}