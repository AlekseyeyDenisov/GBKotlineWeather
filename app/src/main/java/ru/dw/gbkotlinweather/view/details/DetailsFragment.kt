package ru.dw.gbkotlinweather.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.dw.gbkotlinweather.databinding.FragmentDetailsBinding
import ru.dw.gbkotlinweather.repository.Weather

const val KEY_BUNDLE_WEATHER = "KEY_BUNDLE_WEATHER"

class DetailsFragment : Fragment() {
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
        val weather: Weather = arguments?.getParcelable<Weather>(KEY_BUNDLE_WEATHER) as Weather
        render(weather)
    }

    private fun render(weather: Weather) {
        binding.loadingLayout.visibility = View.GONE
        binding.cityName.text = weather.city.name
        binding.cityCoordinates.text = StringBuilder("${weather.city.lat} ${weather.city.lon}")
        binding.feelsLikeValue.text = weather.feelsLike.toString()
        binding.temperatureValue.text = weather.temperature.toString()
        Snackbar.make(binding.mainView, "Все работает", Snackbar.LENGTH_LONG)
    }

    companion object {
        fun newInstance(bundel: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundel
            return fragment
        }
    }

    override fun onDestroy() {
        _banding = null
        super.onDestroy()
    }
}