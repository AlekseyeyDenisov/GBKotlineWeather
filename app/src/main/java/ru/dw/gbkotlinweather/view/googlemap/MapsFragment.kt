package ru.dw.gbkotlinweather.view.googlemap

import android.Manifest
import android.app.AlertDialog
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
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import ru.dw.gbkotlinweather.MyApp
import ru.dw.gbkotlinweather.R
import ru.dw.gbkotlinweather.model.City
import ru.dw.gbkotlinweather.model.Weather
import ru.dw.gbkotlinweather.utils.CURRENT_USER_KEY
import ru.dw.gbkotlinweather.utils.TAG
import ru.dw.gbkotlinweather.view.details.DetailsFragment
import ru.dw.gbkotlinweather.view.details.KEY_BUNDLE_WEATHER



class MapsFragment : Fragment() {
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

    private fun setMarkerUser(location: Location) {
        val latLng = LatLng(location.latitude, location.longitude)
        val marker = viewModel.myMarker[CURRENT_USER_KEY]
        if (marker == null) {
            val markerOptions = MarkerOptions()
                .position(latLng)
                .title("Вы")
            val newMarker: Marker = myMap.addMarker(markerOptions)!!
            myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13F))
            viewModel.myMarker[CURRENT_USER_KEY] = newMarker
        } else {
            marker.position = latLng
            myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13F))

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
                    if(address[0].locality != null){
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
                    }else{
                        Toast.makeText(requireContext(), getString(R.string.address_not_faunt), Toast.LENGTH_SHORT).show()
                    }



                }

            }.start()
        }

        myMap.setOnMapLongClickListener {
//            val geofencingClient = LocationServices.getGeofencingClient(requireContext())
//            val geofence = Geofence.Builder()
//                .setRequestId((1..100).random().toString()) // Geofence ID
//                .setCircularRegion(it.latitude, it.longitude, 100F) // задаём зону
//                .setExpirationDuration(100L) // продолжительность мониторинга
//                // Тип направления: вход/выход из зоны
//                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
//                .build()

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)


    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.stopLocation()
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
}