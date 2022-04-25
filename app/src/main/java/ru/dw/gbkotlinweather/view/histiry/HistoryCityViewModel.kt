package ru.dw.gbkotlinweather.view.histiry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.dw.gbkotlinweather.model.Weather
import ru.dw.gbkotlinweather.repository.DetailsRepositoryImpl
import ru.dw.gbkotlinweather.repository.HistoryRepository
import ru.dw.gbkotlinweather.view.details.DetailsViewModel
import ru.dw.gbkotlinweather.view.state.ListState

class HistoryCityViewModel(
    private val liveDate: MutableLiveData<ListState> = MutableLiveData(),
    private val repository: HistoryRepository = DetailsRepositoryImpl()
) : ViewModel() {
    fun getLiveData(): LiveData<ListState> {
        return liveDate
    }
    init {
        getDataWeather()
    }

    private fun getDataWeather() {
        liveDate.postValue(ListState.Loading)
        repository.getAllWeatherDetails(object: DetailsViewModel.CallbackResponseForAll{
            override fun onResponseSuccess(weather: List<Weather>) {
                val resultSuccess  = ListState.Success(weather)
                liveDate.postValue(resultSuccess)
            }

            override fun onFail(error: String) {
                val resultFail  = ListState.Error(Throwable(error))
                liveDate.postValue(resultFail)
            }

        })
    }

}