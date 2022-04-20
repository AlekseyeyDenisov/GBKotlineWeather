package ru.dw.gbkotlinweather.view.weatherlist.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.dw.gbkotlinweather.databinding.ItemRecyclerWeahterBinding
import ru.dw.gbkotlinweather.model.Weather

class WeatherListAdapter(
    private val onItemClickListener: OnItemClickListener,
    private var data: List<Weather> = listOf()
) :
    RecyclerView.Adapter<WeatherListAdapter.CityHolder>() {


    fun setData(newData: List<Weather>) {
        val callback = WeatherListDiffUtilCallback(data,newData)
        val diffUtilCallback = DiffUtil.calculateDiff(callback)
        diffUtilCallback.dispatchUpdatesTo(this)
        this.data = newData
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CityHolder {
        val binding =
            ItemRecyclerWeahterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
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