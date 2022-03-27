package ru.dw.gbkotlinweather.repository

interface Repository {
    fun getWeatherFromServer():Weather
    fun getWeatherFromLocalStorage():Weather

}