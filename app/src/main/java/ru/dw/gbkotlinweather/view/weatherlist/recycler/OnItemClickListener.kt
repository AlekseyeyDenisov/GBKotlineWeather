package ru.dw.gbkotlinweather.view.weatherlist.recycler

import ru.dw.gbkotlinweather.model.Weather

interface OnItemClickListener {
    fun onItemClick(weather: Weather)
}