package ru.dw.gbkotlinweather.repository.api_yandex


import ru.dw.gbkotlinweather.model.Weather

interface OnServerResponseListener {
    fun onResponseSuccess(weather: Weather)
    fun error(error: String)
}