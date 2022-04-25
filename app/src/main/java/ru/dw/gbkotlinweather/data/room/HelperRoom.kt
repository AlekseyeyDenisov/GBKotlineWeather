package ru.dw.gbkotlinweather.data.room

import ru.dw.gbkotlinweather.model.City
import ru.dw.gbkotlinweather.model.Weather
import ru.dw.gbkotlinweather.repository.DetailsRepository
import ru.dw.gbkotlinweather.repository.HistoryRepository
import ru.dw.gbkotlinweather.utils.convertHistoryEntityToWeather
import ru.dw.gbkotlinweather.utils.convertListHistoryEntityToWeather
import ru.dw.gbkotlinweather.utils.convertWeatherToEntity
import ru.dw.gbkotlinweather.view.details.DetailsViewModel

class HelperRoom(private val db: HistoryDao) : DetailsRepository, HistoryRepository {
    override fun getWeatherDetails(
        city: City,
        callbackDetailsWeather: DetailsViewModel.CallbackDetails
    ) {
        val detailsCity = db.getHistoryForCity(city.name)
        if (detailsCity!= null)
            callbackDetailsWeather.onResponseSuccess(convertHistoryEntityToWeather(detailsCity))
        else callbackDetailsWeather.onFail("По ${city.name} городу нет истории запросов")

    }

    override fun getAllWeatherDetails(callbackWeather: DetailsViewModel.CallbackResponseForAll) {
        callbackWeather.onResponseSuccess(convertListHistoryEntityToWeather(db.getAll()))
    }

    override fun addWeather(weather: Weather) {
        Thread {
            db.insert(convertWeatherToEntity(weather))
        }.start()
    }
}