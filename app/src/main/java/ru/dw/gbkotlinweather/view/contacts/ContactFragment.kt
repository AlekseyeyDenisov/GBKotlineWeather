package ru.dw.gbkotlinweather.view.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.dw.gbkotlinweather.databinding.FragmentDetailsBinding
import ru.dw.gbkotlinweather.databinding.FragmentUserContactBinding
import ru.dw.gbkotlinweather.model.Weather
import ru.dw.gbkotlinweather.utils.getUrlYandexSvgIcon
import ru.dw.gbkotlinweather.utils.loadSvg
import ru.dw.gbkotlinweather.view.state.DetailsState


class ContactFragment : Fragment() {
    private var _banding: FragmentUserContactBinding? = null
    private val binding get() = _banding!!

    private val viewModelDetails: ContactViewModel by lazy {
        ViewModelProvider(this).get(ContactViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _banding = FragmentUserContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val observer = Observer<DetailsState> {

        }
        viewModelDetails.getLiveDataCityWeather().observe(viewLifecycleOwner, observer)

    }


    companion object {
        fun newInstance(): ContactFragment {
            val fragment = ContactFragment()
            return fragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _banding = null
    }



}