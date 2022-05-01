package ru.dw.gbkotlinweather.data.google_maps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import ru.dw.gbkotlinweather.utils.LOCATION_REFRESH_DISTANCE
import ru.dw.gbkotlinweather.utils.LOCATION_REFRESH_TIME
import ru.dw.gbkotlinweather.utils.TAG

class HelperLocation(private val context: Context) {
    private val liveDataLocation = MutableLiveData<Location>()
    fun start() = updateLocation()

    fun stop()= run {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.removeUpdates(locationListener)
    }
    fun getLiveDataLocation() = liveDataLocation




    private val locationListener = object :LocationListener{

        override fun onLocationChanged(location: Location) {
            liveDataLocation.postValue(location)
            Log.d(TAG, "onLocationChanged: $location")
        }

        override fun onProviderDisabled(provider: String) {
            super.onProviderDisabled(provider)
        }

        override fun onProviderEnabled(provider: String) {
            super.onProviderEnabled(provider)
        }

    }

    private fun updateLocation(){
        context.let {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED  &&
                ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_COARSE_LOCATION )!= PackageManager.PERMISSION_GRANTED ) {
                return
            }

            val locationManager = it.getSystemService(Context.LOCATION_SERVICE) as LocationManager


            if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

                locationManager.allProviders.let{

                }
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    LOCATION_REFRESH_TIME,
                    LOCATION_REFRESH_DISTANCE,
                    locationListener
                )
            }
        }
    }



}