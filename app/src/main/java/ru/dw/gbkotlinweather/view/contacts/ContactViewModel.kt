package ru.dw.gbkotlinweather.view.contacts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.dw.gbkotlinweather.view.state.DetailsState


class ContactViewModel(
    private val liveDate: MutableLiveData<DetailsState> = MutableLiveData()
) : ViewModel() {
    //private val repository: DetailsRepository =  WeatherLoader()



    fun getLiveDataCityWeather() = liveDate



}