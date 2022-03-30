package ru.dw.gbkotlinweather.view.weatherlist

import ru.dw.gbkotlinweather.repository.Weather

interface OnItemClickListener {
    fun onItemClick(weather: Weather)
}