package ru.dw.gbkotlinweather.repository

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(
    val city: City = getDefaultCity(),
    val temperature: Int = 0,
    val feelsLike: Int = 0
):Parcelable
@Parcelize
data class City(val name: String, val lat: Double, val lon: Double):Parcelable

fun getDefaultCity() = City("Москва", 55.75, 37.61)



