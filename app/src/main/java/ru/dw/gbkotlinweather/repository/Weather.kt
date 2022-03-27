package ru.dw.gbkotlinweather.repository

class Weather(val city: City = getDefault(), val temperature: Int = 0, val feelsLike: Int = 0)

fun getDefault() = City("Москва", 55.75, 37.61)

data class City(val name: String, val lat: Double, val lon: Double)