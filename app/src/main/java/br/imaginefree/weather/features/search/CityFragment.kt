package br.imaginefree.weather.features.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.imaginefree.weather.R
import br.imaginefree.weather.data.local.AppDatabase
import br.imaginefree.weather.data.model.BaseResponse
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.data.network.Service
import br.imaginefree.weather.databinding.FragmentCityBinding
import br.imaginefree.weather.features.adapter.AdapterType
import br.imaginefree.weather.features.adapter.CityAdapter
import br.imaginefree.weather.features.forecast.ForecastActivity
import kotlin.concurrent.thread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CityFragment : Fragment(R.layout.fragment_city){

    private lateinit var binding: FragmentCityBinding
    private lateinit var cityAdapter: CityAdapter<City>
    private val cities = ArrayList<City>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCityBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cityAdapter = CityAdapter(cities, cities, AdapterType.SEARCH, ::startForecastActivity)
        binding.weatherList.adapter = cityAdapter
        binding.weatherList.layoutManager = LinearLayoutManager(requireContext())
        binding.btnSearch.setOnClickListener {
            getCities(binding.searchField.text.toString())
        }
        getInternalElements()
    }

    private fun startForecastActivity(city: Any){
        val intent = Intent(requireContext(), ForecastActivity::class.java)
        intent.putExtra( "CITY", city as City)
        startActivity(intent)
    }

    private fun getInternalElements(){
        thread {
            val lista = AppDatabase.getInstance(requireContext())?.cityDao()?.getCities()
            cities.clear()
            lista?.forEach { citiesAndWeathers ->
                citiesAndWeathers.city.weather = citiesAndWeathers.weather
                cities.add(citiesAndWeathers.city)
            }
            activity?.runOnUiThread {
                cityAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun getCities(searchField: String){
        Service
            .getService()
            .getCity(searchField, "metric", "PT", "b02f5abb291a5a402a86d45e3807c357")
            .enqueue(object : Callback<BaseResponse<City>> {
                override fun onResponse(
                    call: Call<BaseResponse<City>>,
                    response: Response<BaseResponse<City>>
                ) {
                    response.body()?.list?.let {
                        cities.clear()
                        cities.addAll(it)
                        cityAdapter.notifyDataSetChanged()
                        it.forEach{ city ->
                            thread {
                                AppDatabase.getInstance(requireContext())?.cityDao()?.insert(city)
                                city.weather.forEach { weather ->
                                    weather.weatherOwnerId = city.cityId
                                    AppDatabase.getInstance(requireContext())?.weatherDao()?.insert(weather)
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<BaseResponse<City>>, t: Throwable) {
                    t.printStackTrace()
                }

            })
    }

}