package ru.dw.gbkotlinweather.repository

interface Repository {
    fun getDataServer(): Weather
    fun getDataLocal():Weather
}