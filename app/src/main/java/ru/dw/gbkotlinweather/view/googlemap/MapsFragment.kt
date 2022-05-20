package ru.dw.gbkotlinweather.view.googlemap

import android.Manifest
import android.app.AlertDialog
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import ru.dw.gbkotlinweather.MyApp
import ru.dw.gbkotlinweather.R
import ru.dw.gbkotlinweather.databinding.FragmentMapsBinding
import ru.dw.gbkotlinweather.model.City
import ru.dw.gbkotlinweather.model.Weather
import ru.dw.gbkotlinweather.utils.CURRENT_USER_KEY
import ru.dw.gbkotlinweather.utils.TAG
import ru.dw.gbkotlinweather.view.details.DetailsFragment
import ru.dw.gbkotlinweather.view.details.KEY_BUNDLE_WEATHER
import java.io.IOException


class MapsFragment : Fragment() {
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private val pref = MyApp.pref


    private val viewModel: MapsViewModel by lazy {
        ViewModelProvider(this).get(MapsViewModel::class.java)
    }

    private lateinit var myMap: GoogleMap

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    )
    {
        if (it) {
            viewModel.startLocation()
            viewModel.getLocation().observe(viewLifecycleOwner) { location ->
                setMarkerUser(location)


            }
        } else {
            var nextNumberDenial = pref.getPermitsLocation()
            nextNumberDenial++
            pref.setPermitsLocation(nextNumberDenial)
            if (nextNumberDenial <= 2) {
                repeatMessageRequest(
                    getString(R.string.permision_gps),
                    getString(R.string.message_permision_gps)
                )
            } else {
                messageNoPermission()
            }
        }

    }


    private val callback = OnMapReadyCallback { googleMap ->
        myMap = googleMap
        myMap.uiSettings.isZoomControlsEnabled = true
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        recoveryMarker()
        myMap.setOnMapClickListener { location ->
            val geocoder = Geocoder(requireContext())
            Thread {
                val address = geocoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1
                )

                Handler(Looper.getMainLooper()).post {
                    if (address[0].locality != null) {
                        val bundle = Bundle()
                        val weather = Weather(
                            City(
                                address[0].locality,
                                location.latitude,
                                location.longitude
                            )
                        )
                        bundle.putParcelable(KEY_BUNDLE_WEATHER, weather)
                        Log.d(TAG, "address : " + address[0].locality)
                        requireActivity().supportFragmentManager.beginTransaction()
                            .add(
                                R.id.container, DetailsFragment.newInstance(bundle)
                            ).addToBackStack("").commit()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.address_not_fount),
                            Toast.LENGTH_SHORT
                        ).show()
                    }


                }

            }.start()
        }
    }

    private fun recoveryMarker() {
        val oldMarker = viewModel.myMarker[CURRENT_USER_KEY]
        if (oldMarker != null) {
            myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(oldMarker.position, 13F))
            val newMarker = myMap.addMarker(
                MarkerOptions()
                    .position(oldMarker.position)
                    .title(oldMarker.title)
            )
            viewModel.myMarker[CURRENT_USER_KEY] = newMarker!!
        }
    }

    private fun searchByAddress() {
        binding.buttonSearch.setOnClickListener {
            val geoCoder = Geocoder(it.context)
            val searchText = binding.searchAddress.text.toString()
            Thread {
                try {
                    val addresses = geoCoder.getFromLocationName(searchText, 1)
                    if (addresses.size > 0) {

                        goToAddress(addresses, it, searchText)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }.start()
        }
    }

    private fun goToAddress(
        addresses: MutableList<Address>,
        view: View,
        searchText: String
    ) {
        val location = LatLng(
            addresses[0].latitude,
            addresses[0].longitude
        )
        view.post {
            setMarker(location, searchText, BitmapDescriptorFactory.HUE_GREEN)
            myMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    location,
                    15f
                )
            )
        }
    }

    private fun setMarkerUser(location: Location) {
        val latLng = LatLng(location.latitude, location.longitude)
        val marker = viewModel.myMarker[CURRENT_USER_KEY]
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13F))
        if (marker == null) {
            viewModel.myMarker[CURRENT_USER_KEY] = setMarker(
                latLng,
                "Вы",
                BitmapDescriptorFactory.HUE_AZURE
            )
        } else {
            marker.position = latLng

        }

    }

    private fun setMarker(
        location: LatLng,
        searchText: String,
        resourceId: Float
    ): Marker {
        return myMap.addMarker(
            MarkerOptions()
                .position(location)
                .title(searchText)
                .icon(BitmapDescriptorFactory.defaultMarker(resourceId))
        )!!
    }


    private fun repeatMessageRequest(title: String, message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(getString(R.string.permission_required) + message)
            .setPositiveButton(getString(R.string.grant_access)) { _, _ ->
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            .setNegativeButton(getString(R.string.go_back)) { dialog, _ ->
                requireActivity().onBackPressed()
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun messageNoPermission() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.permission_required)
            .setMessage(getString(R.string.message_reinstal_application))
            .setNegativeButton(getString(R.string.go_back)) { dialog, _ ->
                dialog.dismiss()
                requireActivity().onBackPressed()
            }
            .create()
            .show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        searchByAddress()
    }


    override fun onDestroy() {
        super.onDestroy()
        viewModel.stopLocation()
    }


}