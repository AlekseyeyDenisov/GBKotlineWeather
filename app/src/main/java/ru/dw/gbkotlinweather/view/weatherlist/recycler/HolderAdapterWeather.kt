package ru.dw.gbkotlinweather.view.weatherlist.recycler


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.dw.gbkotlinweather.databinding.ItemRecyclerWeahterBinding
import ru.dw.gbkotlinweather.model.Weather

class HolderAdapterWeather(
    view: View,
    private val onItemClickListenerListCity: OnItemClickListenerListCity
) : RecyclerView.ViewHolder(view) {
    fun bind(weather: Weather) {
        ItemRecyclerWeahterBinding.bind(itemView).apply {
            cityName.text = weather.city.name
            root.setOnClickListener {
                onItemClickListenerListCity.onItemClick(weather)
            }
        }
    }

}