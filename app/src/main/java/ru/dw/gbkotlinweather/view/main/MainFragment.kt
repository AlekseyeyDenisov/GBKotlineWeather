package ru.dw.gbkotlinweather.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.dw.gbkotlinweather.databinding.FragmentMainBinding
import ru.dw.gbkotlinweather.viewmodel.AppSate
import ru.dw.gbkotlinweather.viewmodel.MainViewModel


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val observer = object : Observer<AppSate> {
            override fun onChanged(data: AppSate) {
                renderData(data)
            }

        }
        viewModel.getLiveDataWeather().observe(viewLifecycleOwner, observer)
        viewModel.getWeather()
    }

    private fun renderData(data: AppSate) {
        when (data) {
            is AppSate.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.mainView, data.error.toString(), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Обновить", {
                        viewModel.getWeather()
                    })
                    .show()
            }
            is AppSate.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppSate.Success -> {
                binding.cityName.text = data.weather.city.name
                binding.cityCoordinates.text = "${data.weather.city.lat} ${data.weather.city.lon}"
                binding.feelsLikeValue.text = data.weather.feelsLike.toString()
                binding.temperatureValue.text = data.weather.temperature.toString()
                Snackbar.make(binding.mainView, "Все получилось", Snackbar.LENGTH_LONG).show()
                binding.loadingLayout.visibility = View.GONE
            }
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}