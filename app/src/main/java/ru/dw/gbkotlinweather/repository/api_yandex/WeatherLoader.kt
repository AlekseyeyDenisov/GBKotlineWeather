package ru.dw.gbkotlinweather.repository.api_yandex

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import ru.dw.gbkotlinweather.repository.model.Weather
import ru.dw.gbkotlinweather.utils.*
import ru.dw.gbkotlinweather.view.viewmodel.ResponseState
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class WeatherLoader(private val onServerResponseListener: OnServerResponseListener) {


    fun getCityWeather(weather: Weather) {
        val latLong = "?lat=${weather.city.lat}&lon=${weather.city.lon}"
        val urlText = "$YANDEX_DOMAIN$YANDEX_PATH$latLong"
        val uri = URL(urlText)
        Thread {
            val urlConnection: HttpURLConnection =
                (uri.openConnection() as HttpURLConnection).apply {
                    connectTimeout = CONNECTION_TIMEOUT
                    readTimeout = READ_TIMEOUT
                    addRequestProperty(YANDEX_API_KEY, API_KEY)
                }
            val headers = urlConnection.headerFields
            Log.d("@@@", "headers: $headers")
            val responseCode = urlConnection.responseCode
            Log.d("@@@", "responseCode: $responseCode")
            val responseMessage = urlConnection.responseMessage
            Log.d("@@@", "responseMessage: $responseMessage")

            val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val weatherDTO: WeatherDTO = Gson().fromJson(buffer, WeatherDTO::class.java)


            val weatherResult = map(weather, weatherDTO)
            Log.d("@@@", "getCityWeather: $weatherResult")
            Handler(Looper.getMainLooper()).post {
                onServerResponseListener.onResponse(ResponseState.OnResponseSuccess(weatherResult))
            }

        }.start()

    }

    private fun map(weather: Weather, weatherDTO: WeatherDTO): Weather {
        weatherDTO.fact?.feelsLike?.let {
            weather.feelsLike = it

        }
        weatherDTO.fact?.temp?.let {
            weather.temperature = it
        }
        return weather
    }
}