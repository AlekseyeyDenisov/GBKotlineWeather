package ru.dw.gbkotlinweather.data.google_maps

import android.Manifest
import android.annotation.SuppressLint
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
    private val locationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    fun start() = updateLocation()

    fun stop() = run {
        locationManager.removeUpdates(locationListener)
    }

    fun getLiveDataLocation() = liveDataLocation



    @SuppressLint("MissingPermission")
    private fun getLastBestLocation() {//последние известное место положение
        val locationGPS: Location? =
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        locationGPS?.let {
            liveDataLocation.postValue(it)
        }
    }


    private val locationListener = object : LocationListener {

        override fun onLocationChanged(location: Location) {
            Log.d(TAG, "onLocationChanged: $location")
            liveDataLocation.postValue(location)
        }

        override fun onFlushComplete(requestCode: Int) {
            Log.d(TAG, "onFlushComplete requestCode: $requestCode")
            super.onFlushComplete(requestCode)
        }

        override fun onLocationChanged(locations: MutableList<Location>) {
            Log.d(TAG, "onLocationChanged locations: $locations")
            super.onLocationChanged(locations)
        }

        override fun onProviderDisabled(provider: String) {
            Log.d(TAG, "onProviderDisabled: $provider")
            super.onProviderDisabled(provider)
        }

        override fun onProviderEnabled(provider: String) {
            Log.d(TAG, "onProviderEnabled: $provider")
            super.onProviderEnabled(provider)
        }

    }

    private fun updateLocation() {
        getLastBestLocation()
        context.let {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }


            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

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