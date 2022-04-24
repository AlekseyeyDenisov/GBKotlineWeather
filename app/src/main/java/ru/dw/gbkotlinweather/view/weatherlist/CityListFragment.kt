package ru.dw.gbkotlinweather.view.weatherlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.dw.gbkotlinweather.MyApp
import ru.dw.gbkotlinweather.R
import ru.dw.gbkotlinweather.databinding.FragmentListBinding
import ru.dw.gbkotlinweather.model.Weather
import ru.dw.gbkotlinweather.utils.showSnackBar
import ru.dw.gbkotlinweather.view.details.DetailsFragment
import ru.dw.gbkotlinweather.view.details.KEY_BUNDLE_WEATHER
import ru.dw.gbkotlinweather.view.viewmodel.state.ListState
import ru.dw.gbkotlinweather.view.viewmodel.CityViewModel
import ru.dw.gbkotlinweather.view.weatherlist.recycler.OnItemClickListenerListCity
import ru.dw.gbkotlinweather.view.weatherlist.recycler.list.WeatherItemAdapter


class CityListFragment : Fragment(), OnItemClickListenerListCity {
    private var _banding: FragmentListBinding? = null
    private val binding get() = _banding!!
    private val viewModel: CityViewModel by lazy {
        ViewModelProvider(this).get(CityViewModel::class.java)
    }

    //private val adapterWeatherList = WeatherListAdapter(this)                   //list Adapter
    private val adapterWeatherList = WeatherItemAdapter(this) //Item Adapter

    private val pref = MyApp.getPref()
    private var isRussian = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _banding = FragmentListBinding.inflate(inflater, container, false)
        initRecycler()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
        initFloatingButton(pref.getDefaultFloatingActionButton())
    }

    private fun initObserve() {
        val observer = Observer<ListState> { data -> render(data) }
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
    }

    private fun initFloatingButton(flag: Boolean) {
        viewModel.getDataListCity(flag)

        binding.floatingActionButton.setOnClickListener {
            isRussian = !isRussian
            viewModel.getDataListCity(isRussian)
            pref.setDefaultFloatingActionButton(isRussian)
        }
    }

    private fun initRecycler() {
        binding.listWeatherRecyclerView.adapter = adapterWeatherList
    }

    private fun render(data: ListState) {
        when (data) {
            is ListState.Error -> {
                binding.loadingListLayout.visibility = View.GONE
                binding.loadingListLayout.showSnackBar(data.error.toString(), getString(R.string.updateListCity),
                    {
                    viewModel.getDataListCity(isRussian)
                    setValueFloatingButton(isRussian)
                })
            }
            ListState.Loading -> {
                binding.loadingListLayout.visibility = View.VISIBLE
            }
            is ListState.Success -> {
                binding.loadingListLayout.visibility = View.GONE
                //adapterWeatherList.setData(data.weatherList) //list Adapter
                adapterWeatherList.submitList(data.weatherList)//Item Adapter
                setValueFloatingButton(pref.getDefaultFloatingActionButton())
            }

        }
    }

    private fun setValueFloatingButton(flag:Boolean) {
        if (flag) {
            binding.floatingActionButton.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_russia
                )
            )
        } else {

            binding.floatingActionButton.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_earth
                )
            )
        }

    }

    override fun onItemClick(weather: Weather) {
        val bundle = Bundle()
        bundle.putParcelable(KEY_BUNDLE_WEATHER, weather)
        requireActivity().supportFragmentManager.beginTransaction()
            .add(
                R.id.container,
                DetailsFragment.newInstance(bundle)
            ).addToBackStack("").commit()
    }

    companion object {
        fun newInstance() = CityListFragment()
    }

    override fun onDestroy() {
        _banding = null
        super.onDestroy()
    }
}