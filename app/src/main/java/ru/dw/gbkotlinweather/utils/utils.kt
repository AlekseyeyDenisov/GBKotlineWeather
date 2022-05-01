package ru.dw.gbkotlinweather.utils


import android.view.View
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.google.android.material.snackbar.Snackbar
import ru.dw.gbkotlinweather.BuildConfig
import ru.dw.gbkotlinweather.model.City
import ru.dw.gbkotlinweather.model.Weather
import ru.dw.gbkotlinweather.model.getDefaultCity
import ru.dw.gbkotlinweather.data.api_yandex.Fact
import ru.dw.gbkotlinweather.data.api_yandex.WeatherDTO
import ru.dw.gbkotlinweather.data.room.HistoryEntity

const val TAG = "@@@"
const val YANDEX_DOMAIN = "https://api.weather.yandex.ru/"
const val YANDEX_DOMAIN_HARD_MODE = "http://212.86.114.27/"
const val YANDEX_POINT = "v2/informers"
const val YANDEX_API_KEY = "X-Yandex-API-Key"
const val API_KEY = BuildConfig.WEATHER_API_KEY
const val CONNECTION_TIMEOUT = 1000
const val READ_TIMEOUT = 1000
const val CONSTANT_LAT = "lat"
const val CONSTANT_LON = "lon"
const val TAG_FRAGMENT_HISTORY = "fragment-history"
const val TAG_FRAGMENT_CONTACT = "fragment-contact"
const val TAG_FRAGMENT_MAP = "fragment-map"
//location
const val LOCATION_REFRESH_TIME = 5000L
const val LOCATION_REFRESH_DISTANCE = 50F
const val CURRENT_USER_KEY = "current user"



fun View.showSnackBar(
    text: String,
    actionText: String,
    action: (View) -> Unit,
    length: Int = Snackbar.LENGTH_INDEFINITE
) {
    Snackbar.make(this, text, length).setAction(actionText, action).show()
}


fun convertDtoToModel(weatherDTO: WeatherDTO): Weather {
    val fact: Fact = weatherDTO.fact
    return Weather(getDefaultCity(), fact.temp, fact.feelsLike, fact.icon)
}

fun convertListHistoryEntityToWeather(entity: List<HistoryEntity>): List<Weather> {
    return entity.map {
        Weather(City(it.city, it.lat, it.lon), it.temperature, it.feelsLike, it.icon)
    }
}
fun convertHistoryEntityToWeather(entity: HistoryEntity):Weather {
    entity.also {
        return Weather(City(it.city, it.lat, it.lon), it.temperature, it.feelsLike, it.icon)
    }

}


fun convertWeatherToEntity(weather: Weather): HistoryEntity {

    return HistoryEntity(
        0,
        weather.city.name,
        weather.city.lat,
        weather.city.lon,
        weather.temperature,
        weather.feelsLike,
        weather.icon
    )
}

fun ImageView.loadSvg(url: String) {
    val imageLoader = ImageLoader.Builder(this.context)
        .componentRegistry { add(SvgDecoder(this@loadSvg.context)) }
        .build()
    val request = ImageRequest.Builder(this.context)
        .crossfade(true)
        .crossfade(500)
        .data(url)
        .target(this)
        .build()
    imageLoader.enqueue(request)
}

fun getUrlYandexSvgIcon(name:String):String{
    return "https://yastatic.net/weather/i/icons/blueye/color/svg/${name}.svg"
}


