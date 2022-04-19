package ru.dw.gbkotlinweather.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.google.android.material.snackbar.Snackbar
import ru.dw.gbkotlinweather.databinding.FragmentDetailsBinding
import ru.dw.gbkotlinweather.model.Weather
import ru.dw.gbkotlinweather.view.viewmodel.DetailsState
import ru.dw.gbkotlinweather.view.viewmodel.DetailsViewModel

const val KEY_BUNDLE_WEATHER = "KEY_BUNDLE_WEATHER"

class DetailsFragment : Fragment() {
    private var _banding: FragmentDetailsBinding? = null
    private val binding get() = _banding!!

    private val viewModelDetails: DetailsViewModel by lazy {
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

        viewModelDetails.upDataWeather(weather.city)
        val observer = Observer<DetailsState> { data -> getResponse(data) }
        viewModelDetails.getLiveDataCityWeather().observe(viewLifecycleOwner, observer)

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

    private fun getResponse(response: DetailsState) {
        when (response) {
            is DetailsState.Success -> {
                binding.loadingDetailsLayout.visibility = View.GONE
                render(response.weather)
            }
            is DetailsState.Error -> {
                binding.loadingDetailsLayout.visibility = View.GONE
                Snackbar.make(binding.mainView, response.error.message!!, Snackbar.LENGTH_LONG)
                    .show()
            }
            DetailsState.Loading -> {
                binding.loadingDetailsLayout.visibility = View.VISIBLE
            }
        }

    }

    private fun render(weather: Weather) {
        with(binding) {
            loadingDetailsLayout.visibility = View.GONE
            cityName.text = weather.city.name
            cityCoordinates.text = StringBuilder("${weather.city.lat} ${weather.city.lon}")
            feelsLikeValue.text = weather.feelsLike.toString()
            temperatureValue.text = weather.temperature.toString()
            headerIcon.loadSvg("https://yastatic.net/weather/i/icons/blueye/color/svg/${weather.icon}.svg")
        }


    }

    private fun ImageView.loadSvg(url: String) {
        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry { add(SvgDecoder(this@loadSvg.context)) }
            .build()
        val request = ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(500)
            .data(url)
            .target(this)
            .build()
        imageLoader.enqueue(request)
    }
}