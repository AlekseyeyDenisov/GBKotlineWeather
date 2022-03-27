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
import ru.dw.gbkotlinweather.viewmodel.AppState
import ru.dw.gbkotlinweather.viewmodel.MainViewModel


class MainFragment : Fragment() {
    private var _banding:FragmentMainBinding? = null
    private val binding get() = _banding!!
    private val viewModel:MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _banding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val observer = object:Observer<AppState>{
            override fun onChanged(data: AppState) {
                render(data)
            }
        }
        viewModel.getLiveData().observe(viewLifecycleOwner,observer)
        viewModel.getDataWeather()
    }

    private fun render(data: AppState) {
        when(data){
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.mainView,data.error.toString(),Snackbar.LENGTH_INDEFINITE)
                    .setAction("Попробывать еще раз",{
                        viewModel.getDataWeather()
                    })
                    .show()
            }
            AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                binding.cityName.text = data.data.city.name
                binding.cityCoordinates.text = StringBuilder("${data.data.city.lat} ${data.data.city.lon}")
                binding.feelsLikeValue.text = data.data.feelsLike.toString()
                binding.temperatureValue.text = data.data.temperature.toString()
                Snackbar.make(binding.mainView,"Все работает",Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onDestroy() {
        _banding = null
        super.onDestroy()
    }
}