package ru.dw.gbkotlinweather.repository

interface Repository {
    fun getDataWeatherServer():Weather
    fun getDataLocal():Weather
}