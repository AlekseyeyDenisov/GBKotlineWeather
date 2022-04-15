package ru.dw.gbkotlinweather.view.weatherlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.dw.gbkotlinweather.databinding.ItemRecyclerWeahterBinding
import ru.dw.gbkotlinweather.repository.model.Weather

class WeatherListAdapter(
    private val onItemClickListener: OnItemClickListener,
    private var data: List<Weather> = listOf()
) :
    RecyclerView.Adapter<WeatherListAdapter.CityHolder>() {


    fun setData(newData: List<Weather>) {
        this.data = newData
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherListAdapter.CityHolder {
        val binding =
            ItemRecyclerWeahterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityHolder(binding.root)
    }

    override fun onBindViewHolder(holder: WeatherListAdapter.CityHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class CityHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(weather: Weather) {
            ItemRecyclerWeahterBinding.bind(itemView).apply {
                cityName.text = weather.city.name
                root.setOnClickListener {
                    onItemClickListener.onItemClick(weather)
                }
            }
        }
    }
}