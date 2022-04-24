package ru.dw.gbkotlinweather.view.histiry.recycler


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.dw.gbkotlinweather.databinding.ItemHistoryRecyclerWetherBinding
import ru.dw.gbkotlinweather.model.Weather
import ru.dw.gbkotlinweather.utils.getUrlYandexSvgIcon
import ru.dw.gbkotlinweather.utils.loadSvg

class HolderAdapterHistoryWeather(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(weather: Weather) {
        ItemHistoryRecyclerWetherBinding.bind(itemView).apply {
            cityName.text = weather.city.name
            cityCoordinates.text = "${weather.city.lat} ${weather.city.lon} "
            temperatureValue.text = weather.temperature.toString()
            feelsLikeValue.text = weather.feelsLike.toString()
            headerIcon.loadSvg(getUrlYandexSvgIcon(weather.icon))
        }
    }

}