package ru.dw.gbkotlinweather.repository.api_yandex

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import ru.dw.gbkotlinweather.model.City
import ru.dw.gbkotlinweather.repository.DetailsRepository
import ru.dw.gbkotlinweather.utils.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class WeatherLoader : DetailsRepository {

    override fun getWeatherDetails(city: City, callbackWeather: OnServerResponseListener) {
        val latLong = "?lat=${city.lat}&lon=${city.lon}"
        //val urlText = "$YANDEX_DOMAIN$YANDEX_PATH$latLong"
        val urlText = "$YANDEX_DOMAIN_HARD_MODE$YANDEX_POINT$latLong"
        Log.d("@@@", "urlText: $urlText")
        val uri = URL(urlText)
        Thread {
            val urlConnection: HttpURLConnection =
                (uri.openConnection() as HttpURLConnection).apply {
                    connectTimeout = CONNECTION_TIMEOUT
                    readTimeout = READ_TIMEOUT
                    addRequestProperty(YANDEX_API_KEY, API_KEY)
                }
            try {


                val headers = urlConnection.headerFields
                Log.d("@@@", "headers: $headers")
                val responseCode = urlConnection.responseCode
                val responseMessage = urlConnection.responseMessage
                Log.d("@@@", "responseMessage: $responseMessage")

                val maximumNumberError = 600..2000
                val serverside = 500..599
                val clientside = 400..499
                val responseOk = 200..299

                when (responseCode) {
                    in maximumNumberError -> {
                        callbackWeather.error("Не известная ошибка $maximumNumberError")
                        Log.d("@@@", "Не известная ошибка $maximumNumberError")
                    }
                    in serverside -> {
                        Log.d("@@@", "serverside: $serverside")
                        callbackWeather.error(responseMessage)
                    }
                    in clientside -> {
                        Log.d("@@@", "clientside: $clientside")
                        callbackWeather.error(responseMessage)

                    }
                    in responseOk -> {
                        val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
                        val weatherDto: WeatherDTO = Gson().fromJson(buffer, WeatherDTO::class.java)
                        val weather = convertDtoToModel(weatherDto).apply {
                            this.city = city
                        }
                        Log.d("@@@", "getCityWeather: $weather")
                        Handler(Looper.getMainLooper()).post {
                            callbackWeather.onResponseSuccess(weather)
                        }
                    }
                }
            } catch (e: JsonSyntaxException) {
                e.message?.let { callbackWeather.error(it) }

            } finally {
                urlConnection.disconnect()
            }


        }.start()
    }


}