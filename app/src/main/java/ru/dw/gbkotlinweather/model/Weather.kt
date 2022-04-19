package ru.dw.gbkotlinweather.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(
    var city: City = getDefaultCity(),
    var temperature: Int = 0,
    var feelsLike: Int = 0,
    var icon: String = ""
) : Parcelable

@Parcelize
data class City(val name: String, val lat: Double, val lon: Double) : Parcelable

fun getDefaultCity() = City("Москва", 55.75, 37.61)



