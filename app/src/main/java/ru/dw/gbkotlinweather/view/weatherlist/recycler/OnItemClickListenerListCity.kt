package ru.dw.gbkotlinweather.view.weatherlist.recycler

import ru.dw.gbkotlinweather.model.Weather

interface OnItemClickListenerListCity {
    fun onItemClick(weather: Weather)
}