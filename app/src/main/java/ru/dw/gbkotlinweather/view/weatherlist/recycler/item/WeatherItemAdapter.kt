package ru.dw.gbkotlinweather.view.weatherlist.recycler.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.dw.gbkotlinweather.databinding.ItemRecyclerWeahterBinding
import ru.dw.gbkotlinweather.model.Weather
import ru.dw.gbkotlinweather.view.weatherlist.recycler.HolderAdapterWeather
import ru.dw.gbkotlinweather.view.weatherlist.recycler.OnItemClickListenerListCity

class WeatherItemAdapter(
    private val onItemClickListenerListCity: OnItemClickListenerListCity,
    private var data: List<Weather> = listOf()
):ListAdapter<Weather, HolderAdapterWeather>(WeatherItemDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderAdapterWeather {
        val binding = ItemRecyclerWeahterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HolderAdapterWeather(binding.root,onItemClickListenerListCity)
    }

    override fun onBindViewHolder(holder: HolderAdapterWeather, position: Int) {
        //holder.bind(data[position])//List
        holder.bind(getItem(position))//Item
    }




}