package ru.dw.gbkotlinweather.view.weatherlist.recycler.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.dw.gbkotlinweather.databinding.ItemRecyclerWeahterBinding
import ru.dw.gbkotlinweather.model.Weather
import ru.dw.gbkotlinweather.view.weatherlist.recycler.HolderAdapterWeather
import ru.dw.gbkotlinweather.view.weatherlist.recycler.OnItemClickListenerListCity
import ru.dw.gbkotlinweather.view.weatherlist.recycler.item.WeatherListDiffUtilCallback

class WeatherListAdapter(
    private val onItemClickListenerListCity: OnItemClickListenerListCity,
    private var data: List<Weather> = listOf()
) :
    RecyclerView.Adapter<HolderAdapterWeather>() {


    fun setData(newData: List<Weather>) {
        val callback = WeatherListDiffUtilCallback(data,newData)
        val diffUtilCallback = DiffUtil.calculateDiff(callback)
        diffUtilCallback.dispatchUpdatesTo(this)
        this.data = newData
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): HolderAdapterWeather {
        val binding =
            ItemRecyclerWeahterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HolderAdapterWeather(binding.root,onItemClickListenerListCity)
    }

    override fun onBindViewHolder(holder: HolderAdapterWeather, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size


}