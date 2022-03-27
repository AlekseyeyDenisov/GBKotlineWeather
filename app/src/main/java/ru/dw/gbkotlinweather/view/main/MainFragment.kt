package ru.dw.gbkotlinweather.view.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.dw.gbkotlinweather.databinding.FragmentMainBinding
import ru.dw.gbkotlinweather.viewmodel.AppState
import ru.dw.gbkotlinweather.viewmodel.MainViewModel


class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    lateinit var  viewModel : MainViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val observe = object :Observer<AppState>{
            override fun onChanged(data: AppState) {
                renderData(data)
            }
        }
        viewModel.getLiveData().observe(viewLifecycleOwner,observe)

        viewModel.getWeather()
    }

    @SuppressLint("SetTextI18n")
    private fun renderData(data:AppState){
        when (data){
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.mainView,data.error.toString(),Snackbar.LENGTH_LONG)
                    .setAction("Потоить запрос") {
                        viewModel.getWeather()
                    }
                    .show()
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE

            }
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                binding.cityName.text = data.weatherData.city.name
                binding.temperatureValue.text = data.weatherData.temperature.toString()
                binding.feelsLikeValue.text = data.weatherData.feelsLike.toString()
                binding.cityCoordinates.text = "${data.weatherData.city.lat} ${data.weatherData.city.lon}"
                Snackbar.make(binding.mainView,"Получилось",Snackbar.LENGTH_LONG).show()

            }
        }

    }

    companion object {
        fun newInstance() = MainFragment()
    }


}