package ru.dw.gbkotlinweather.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.dw.gbkotlinweather.databinding.FragmentDetailsBinding
import ru.dw.gbkotlinweather.repository.api_yandex.OnServerResponseListener
import ru.dw.gbkotlinweather.repository.api_yandex.WeatherLoader
import ru.dw.gbkotlinweather.repository.model.Weather
import ru.dw.gbkotlinweather.view.viewmodel.ResponseState

const val KEY_BUNDLE_WEATHER = "KEY_BUNDLE_WEATHER"

class DetailsFragment : Fragment(), OnServerResponseListener {
    private var _banding: FragmentDetailsBinding? = null
    private val binding get() = _banding!!


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

        WeatherLoader(this).getCityWeather(weather)
    }

    private fun render(weather: Weather) {
        with(binding) {
            loadingLayout.visibility = View.GONE
            cityName.text = weather.city.name
            cityCoordinates.text = StringBuilder("${weather.city.lat} ${weather.city.lon}")
            feelsLikeValue.text = weather.feelsLike.toString()
            temperatureValue.text = weather.temperature.toString()
        }


        Snackbar.make(binding.mainView, "Все работает", Snackbar.LENGTH_LONG).show()
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

    override fun onResponse(response: ResponseState) {
        when(response){
            is ResponseState.OnResponseSuccess ->{
                render(response.weather)
            }
            is ResponseState.Error ->{

            }


        }

    }
}