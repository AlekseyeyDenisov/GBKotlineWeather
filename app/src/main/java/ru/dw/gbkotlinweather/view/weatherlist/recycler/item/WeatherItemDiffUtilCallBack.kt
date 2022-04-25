package ru.dw.gbkotlinweather.view.weatherlist.recycler.item

import androidx.recyclerview.widget.DiffUtil
import ru.dw.gbkotlinweather.model.Weather

class WeatherItemDiffUtilCallBack:DiffUtil.ItemCallback<Weather>() {
    override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
        return oldItem.city == newItem.city
    }

    override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
        return oldItem == newItem
    }
}