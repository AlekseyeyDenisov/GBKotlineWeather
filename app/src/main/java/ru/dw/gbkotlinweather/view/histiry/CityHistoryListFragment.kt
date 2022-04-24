package ru.dw.gbkotlinweather.view.histiry

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.dw.gbkotlinweather.databinding.FragmentListHistoryBinding
import ru.dw.gbkotlinweather.utils.TAG
import ru.dw.gbkotlinweather.view.histiry.recycler.AdapterWeatherItemHistory
import ru.dw.gbkotlinweather.view.viewmodel.HistoryCityViewModel
import ru.dw.gbkotlinweather.view.viewmodel.state.ListState


class CityHistoryListFragment : Fragment() {
    private var _banding: FragmentListHistoryBinding? = null
    private val binding get() = _banding!!
    private val viewModel: HistoryCityViewModel by lazy {
        ViewModelProvider(this).get(HistoryCityViewModel::class.java)
    }

    private val adapterWeatherList = AdapterWeatherItemHistory()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _banding = FragmentListHistoryBinding.inflate(inflater, container, false)
        initRecycler()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()

    }

    private fun initObserve() {
        val observer = Observer<ListState> { data ->
            render(data)
        }
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
    }


    private fun initRecycler() {
        binding.listWeatherRecyclerView.adapter = adapterWeatherList
    }

    private fun render(data: ListState) {
        when (data) {
            is ListState.Error -> {
                TODO("Доделать")
            }
            is ListState.Success -> {
                data.weatherList.forEach {
                    Log.d(TAG, "render: $it")
                }
                adapterWeatherList.submitList(data.weatherList)
            }

        }
    }


    companion object {
        fun newInstance() = CityHistoryListFragment()
    }

    override fun onDestroy() {
        _banding = null
        super.onDestroy()
    }
}