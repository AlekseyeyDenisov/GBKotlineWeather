package ru.dw.gbkotlinweather.repository.api_yandex.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import ru.dw.gbkotlinweather.repository.api_yandex.WeatherDTO
import ru.dw.gbkotlinweather.utils.*

interface WeatherApi {
    @GET(YANDEX_POINT)
    fun getWeather(
        @Header(YANDEX_API_KEY) apiKey: String,
        @Query(CONSTANT_LAT) lat: Double,
        @Query(CONSTANT_LON) lon: Double
    ): Call<WeatherDTO>
}