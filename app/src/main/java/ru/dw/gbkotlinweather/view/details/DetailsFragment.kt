package ru.dw.gbkotlinweather.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.snackbar.Snackbar
import ru.dw.gbkotlinweather.databinding.FragmentDetailsBinding
import ru.dw.gbkotlinweather.repository.api_yandex.OnServerResponseListener
import ru.dw.gbkotlinweather.repository.model.Weather
import ru.dw.gbkotlinweather.utils.*
import ru.dw.gbkotlinweather.view.viewmodel.ResponseState


class DetailsFragment : Fragment() {
    private var _banding: FragmentDetailsBinding? = null
    private val binding get() = _banding!!

    private val receiver = object :BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.action){
                KEY_WAVE_SERVICE_BROADCAST_SUCCESS ->{
                    binding.loadingLayout.visibility = View.GONE
                    intent.getParcelableExtra<Weather>(KEY_BUNDLE_SERVICE_BROADCAST_WEATHER_SUCCESS)?.let {
                       render(it)
                    }
                }
                KEY_WAVE_SERVICE_BROADCAST_ERROR ->{
                    binding.loadingLayout.visibility = View.GONE
                    val messageError = intent.getStringExtra(
                        KEY_BUNDLE_SERVICE_BROADCAST_WEATHER_ERROR
                    )
                    Toast.makeText(requireContext(), "Ошибка $messageError", Toast.LENGTH_SHORT).show()
                }
            }

        }

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
        val weather = arguments?.getParcelable<Weather>(KEY_BUNDLE_WEATHER_DETAILS) as Weather
        binding.loadingLayout.visibility = View.VISIBLE

        requireActivity().startService(Intent(requireContext(), ServiceWeather::class.java).apply {
            putExtra(KEY_BUNDLE_WEATHER_DETAILS, weather)
        })

        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(receiver,
            IntentFilter(KEY_WAVE_SERVICE_BROADCAST_SUCCESS)
        )
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(receiver,
            IntentFilter(KEY_WAVE_SERVICE_BROADCAST_ERROR)
        )

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
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver)
    }

}