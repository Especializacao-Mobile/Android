package br.imaginefree.weather.features.forecast

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.imaginefree.weather.R
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.data.model.Forecast
import br.imaginefree.weather.databinding.ActivityForecastBinding
import br.imaginefree.weather.features.adapter.CityAdapter
import br.imaginefree.weather.features.adapter.viewholder.ViewHolderType
import br.imaginefree.weather.features.adapter.filter.Filter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForecastActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForecastBinding
    private lateinit var cityAdapter: CityAdapter<Forecast>
    private val forecastList = ArrayList<Forecast>()
    private val forecastViewModel: ForecastViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpObservable()
        setUpForecastRecyclerView()
        (intent.extras?.get(CITY) as? City)?.let { city ->
            setUpView(city)
            forecastViewModel.fetchForecast(city.cityId)
        }
    }

    private fun setUpView(city: City){
        binding.place.text = city.name
        binding.temperature.text = city.main.temp.toString()
        binding.favorite.setOnClickListener {
            forecastViewModel.saveFavorite(city)
        }
    }

    private fun setUpForecastRecyclerView(){
        cityAdapter = CityAdapter(forecastList, ViewHolderType.FORECAST)
        binding.forecastList.adapter = cityAdapter
        binding.forecastList.layoutManager = LinearLayoutManager(this)
    }

    private fun setUpObservable(){
        forecastViewModel.savedCity.observe(this, Observer {
            Toast.makeText(this, getString(R.string.saved_successfully), Toast.LENGTH_LONG).show()
        })

        forecastViewModel.forecast.observe(this, Observer { model ->
            model.data?.let {
                forecastList.clear()
                forecastList.addAll(it.list)
                cityAdapter.notifyDataSetChanged()
                cityAdapter.filter.filter(Filter.NONE)
            }
        })
    }

    companion object {
        const val CITY = "CITY"
    }
}