package ru.dw.gbkotlinweather.view.weatherlist.recycler.list

import androidx.recyclerview.widget.DiffUtil
import ru.dw.gbkotlinweather.model.Weather

class WeatherListDiffUtilCallback(
    private val oldData: List<Weather>,
    private val newData: List<Weather>
) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldData.size


    override fun getNewListSize(): Int = newData.size


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem: Weather = oldData[oldItemPosition]
        val newItem: Weather = newData[newItemPosition]
        return oldItem.city == newItem.city
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem: Weather = oldData[oldItemPosition]
        val newItem: Weather = newData[newItemPosition]
        return oldItem.feelsLike == newItem.feelsLike && oldItem.temperature == newItem.temperature
    }
}