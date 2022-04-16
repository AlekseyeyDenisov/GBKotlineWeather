package ru.dw.gbkotlinweather.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.dw.gbkotlinweather.databinding.FragmentDetailsBinding
import ru.dw.gbkotlinweather.model.Weather
import ru.dw.gbkotlinweather.view.viewmodel.DetailsViewModel
import ru.dw.gbkotlinweather.view.viewmodel.ResponseState

const val KEY_BUNDLE_WEATHER = "KEY_BUNDLE_WEATHER"

class DetailsFragment : Fragment() {
    private var _banding: FragmentDetailsBinding? = null
    private val binding get() = _banding!!

    private val viewModelDetails:DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _banding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weather = arguments?.getParcelable<Weather>(KEY_BUNDLE_WEATHER) as Weather

        viewModelDetails.upDataWeather(weather)
        val observer = Observer<ResponseState> { data -> render(data) }
        viewModelDetails.getLiveDataCityWeather().observe(viewLifecycleOwner, observer)

    }

    private fun render(weather: Weather) {
        with(binding) {
            loadingDetailsLayout.visibility = View.GONE
            cityName.text = weather.city.name
            cityCoordinates.text = StringBuilder("${weather.city.lat} ${weather.city.lon}")
            feelsLikeValue.text = weather.feelsLike.toString()
            temperatureValue.text = weather.temperature.toString()
        }


    }

    companion object {
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _banding = null
    }

     fun render(response: ResponseState) {
        when(response){
            is ResponseState.OnResponseSuccess ->{
                binding.loadingDetailsLayout.visibility = View.GONE
                render(response.weather)
            }
            is ResponseState.Error ->{
                binding.loadingDetailsLayout.visibility = View.GONE
                Snackbar.make(binding.mainView, response.error, Snackbar.LENGTH_LONG).show()
            }
            ResponseState.Loading -> {
                binding.loadingDetailsLayout.visibility = View.VISIBLE
            }
        }

    }
}