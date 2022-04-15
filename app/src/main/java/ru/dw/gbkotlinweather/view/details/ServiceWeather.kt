package ru.dw.gbkotlinweather.view.details

import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.Gson
import ru.dw.gbkotlinweather.repository.api_yandex.WeatherDTO
import ru.dw.gbkotlinweather.repository.model.Weather
import ru.dw.gbkotlinweather.utils.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ServiceWeather(val name: String = "") : IntentService(name) {

    override fun onHandleIntent(intent: Intent?) {
        intent?.let { int ->
            int.getParcelableExtra<Weather>(KEY_BUNDLE_WEATHER_DETAILS)?.let {
                //Log.d("@@@", "onHandleIntent: $it")
                getCityWeather(it)
            }
        }
    }

    private fun getCityWeather(weather: Weather) {
        val latLong = "?lat=${weather.city.lat}&lon=${weather.city.lon}"
        //val urlText = "$YANDEX_DOMAIN$YANDEX_PATH$latLong"
        val urlText = "$YANDEX_DOMAIN_HARD_MODE$YANDEX_PATH$latLong"
        Log.d("@@@", "urlText: $urlText")
        val uri = URL(urlText)

        val urlConnection: HttpURLConnection =
            (uri.openConnection() as HttpURLConnection).apply {
                connectTimeout = CONNECTION_TIMEOUT
                readTimeout = READ_TIMEOUT
                addRequestProperty(YANDEX_API_KEY, API_KEY)
            }
        val headers = urlConnection.headerFields
        Log.d("@@@", "headers: $headers")
        val responseCode = urlConnection.responseCode
        val responseMessage = urlConnection.responseMessage
        Log.d("@@@", "responseMessage: $responseMessage")

        val maximumNumberError = 600..2000
        val serverside = 500..599
        val clientside = 400..499
        val responseOk = 200..299

        val resultServiceWeatherSuccess = Intent(KEY_WAVE_SERVICE_BROADCAST_SUCCESS)
        val resultServiceWeatherError = Intent(KEY_WAVE_SERVICE_BROADCAST_ERROR)


        when (responseCode) {
            in maximumNumberError -> {
                Log.d("@@@", "Не известная ошибка $maximumNumberError")
                resultServiceWeatherError.putExtra(KEY_BUNDLE_SERVICE_BROADCAST_WEATHER_ERROR,"Не известная ошибка $responseMessage")
                LocalBroadcastManager.getInstance(this).sendBroadcast(resultServiceWeatherError)
            }
            in serverside -> {
                Log.d("@@@", "serverside: $serverside")
                resultServiceWeatherError.putExtra(KEY_BUNDLE_SERVICE_BROADCAST_WEATHER_ERROR,responseMessage)
                LocalBroadcastManager.getInstance(this).sendBroadcast(resultServiceWeatherError)
            }
            in clientside -> {
                Log.d("@@@", "clientside: $clientside")
                resultServiceWeatherError.putExtra(KEY_BUNDLE_SERVICE_BROADCAST_WEATHER_ERROR,responseMessage)
                LocalBroadcastManager.getInstance(this).sendBroadcast(resultServiceWeatherError)

            }
            in responseOk -> {
                val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val weatherDTO: WeatherDTO = Gson().fromJson(buffer, WeatherDTO::class.java)
                val weatherResult = map(weather, weatherDTO)
                Log.d("@@@", "getCityWeather: $weatherResult")

                resultServiceWeatherSuccess.putExtra(KEY_BUNDLE_SERVICE_BROADCAST_WEATHER_SUCCESS,weatherResult)
                LocalBroadcastManager.getInstance(this).sendBroadcast(resultServiceWeatherSuccess)
            }
        }


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