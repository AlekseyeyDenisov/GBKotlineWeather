package ru.dw.gbkotlinweather.repository.api_yandex.retrofit

import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.dw.gbkotlinweather.BuildConfig.WEATHER_API_KEY
import ru.dw.gbkotlinweather.model.City
import ru.dw.gbkotlinweather.model.Weather
import ru.dw.gbkotlinweather.repository.api_yandex.DetailsRepository
import ru.dw.gbkotlinweather.repository.api_yandex.OnServerResponseListener
import ru.dw.gbkotlinweather.repository.api_yandex.WeatherDTO
import ru.dw.gbkotlinweather.utils.YANDEX_DOMAIN
import ru.dw.gbkotlinweather.utils.convertDtoToModel

object DetailsRepositoryRetrofit : DetailsRepository {
    private val retrofit: WeatherApi = initRetrofit()

    private fun initRetrofit(): WeatherApi {
        Log.d("@@@", "initRetrofit: ")
        return Retrofit.Builder().apply {
            baseUrl(YANDEX_DOMAIN)
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        }.build().create(WeatherApi::class.java)
    }

    override fun getWeatherDetails(city: City, callbackWeather: OnServerResponseListener) {

        retrofit.getWeather(WEATHER_API_KEY,city.lat,city.lon).enqueue(object :Callback<WeatherDTO>{
            override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
                if (response.isSuccessful){
                    response.body()?.let {weatherDto->
                        val weather = Weather()
                        weather.city = city
                        callbackWeather.onResponseSuccess(convertDtoToModel(weather,weatherDto))
                    }
                }
            }

            override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                t.message?.let { callbackWeather.error(it) }
            }

        })
    }
}