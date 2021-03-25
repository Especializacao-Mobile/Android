package br.imaginefree.weather.features.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.imaginefree.weather.R
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.databinding.FragmentCityBinding
import br.imaginefree.weather.features.adapter.ViewHolderType
import br.imaginefree.weather.features.adapter.CityAdapter
import br.imaginefree.weather.features.adapter.filter.Filter
import br.imaginefree.weather.features.forecast.ForecastActivity

class CityFragment : Fragment(R.layout.fragment_city){

    private lateinit var binding: FragmentCityBinding
    private lateinit var cityAdapter: CityAdapter<City>
    private val cities = ArrayList<City>()
    private val cityFragmentViewModel = CityFragmentViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCityBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cityAdapter = CityAdapter(cities, ViewHolderType.SEARCH, ::startForecastActivity)
        binding.weatherList.adapter = cityAdapter
        binding.weatherList.layoutManager = LinearLayoutManager(requireContext())
        binding.btnSearch.setOnClickListener {
            cityFragmentViewModel.fetchCitiesByName(requireContext(), binding.searchField.text.toString())
        }
        setUpObservable()
    }

    private fun startForecastActivity(city: Any){
        val intent = Intent(requireContext(), ForecastActivity::class.java)
        intent.putExtra(ForecastActivity.CITY, city as City)
        startActivity(intent)
    }

    private fun setUpObservable(){
        cityFragmentViewModel.cityInfoObservable.observe(viewLifecycleOwner, Observer { model ->
            model.data?.list?.let {
                cities.clear()
                cities.addAll(it)
                cityAdapter.notifyDataSetChanged()
                cityAdapter.filter.filter(Filter.NONE)
            }
        })
    }

}