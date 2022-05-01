package ru.dw.gbkotlinweather.view.googlemap

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.maps.model.Marker
import ru.dw.gbkotlinweather.data.google_maps.HelperLocation

class MapsViewModel(application: Application) : AndroidViewModel(application) {
    //private val context = application

    private val helperLocation = HelperLocation(application)

    var myMarker : MutableMap<String, Marker> = java.util.HashMap()

    fun startLocation() = helperLocation.start()
    fun stopLocation() = helperLocation.stop()
    fun getLocation() = helperLocation.getLiveDataLocation()


}