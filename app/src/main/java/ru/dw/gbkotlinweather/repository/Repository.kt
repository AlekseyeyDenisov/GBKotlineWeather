package ru.dw.gbkotlinweather.repository

interface Repository {
    fun getDataServer(): Weather
    fun getWorldWeatherFromLocalStorage():List<Weather>
    fun getRussianWeatherFromLocalStorage():List<Weather>
}