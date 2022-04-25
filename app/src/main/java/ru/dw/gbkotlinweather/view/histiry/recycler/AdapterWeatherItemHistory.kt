package ru.dw.gbkotlinweather.view.histiry.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.dw.gbkotlinweather.databinding.ItemHistoryRecyclerWetherBinding
import ru.dw.gbkotlinweather.databinding.ItemRecyclerWeahterBinding
import ru.dw.gbkotlinweather.model.Weather
import ru.dw.gbkotlinweather.view.weatherlist.recycler.HolderAdapterWeather
import ru.dw.gbkotlinweather.view.weatherlist.recycler.OnItemClickListenerListCity
import ru.dw.gbkotlinweather.view.weatherlist.recycler.item.WeatherItemDiffUtilCallBack

class AdapterWeatherItemHistory():ListAdapter<Weather, HolderAdapterHistoryWeather>(WeatherItemDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderAdapterHistoryWeather {
        val binding = ItemHistoryRecyclerWetherBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HolderAdapterHistoryWeather(binding.root)
    }

    override fun onBindViewHolder(holder: HolderAdapterHistoryWeather, position: Int) {
        //holder.bind(data[position])//List
        holder.bind(getItem(position))//Item
    }




}