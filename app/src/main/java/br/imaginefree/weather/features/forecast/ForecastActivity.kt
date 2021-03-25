package br.imaginefree.weather.features.forecast

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.imaginefree.weather.R
import br.imaginefree.weather.data.local.AppDatabase
import br.imaginefree.weather.data.model.BaseResponse
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.data.model.Forecast
import br.imaginefree.weather.data.network.Service
import br.imaginefree.weather.databinding.ActivityForecastBinding
import br.imaginefree.weather.features.adapter.ViewHolderType
import br.imaginefree.weather.features.adapter.CityAdapter
import br.imaginefree.weather.features.adapter.filter.Filter
import br.imaginefree.weather.utils.Settings
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class ForecastActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForecastBinding
    private lateinit var cityAdapter: CityAdapter<Forecast>
    private val forecastList = ArrayList<Forecast>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val city = intent.extras?.get(CITY) as? City
        binding.place.text = city!!.name
        binding.temperature.text = city.main.temp.toString()
        getForecast(city.cityId)
        binding.favorite.setOnClickListener {
            city.favorite = true
            thread {
                AppDatabase.getInstance(this)?.cityDao()?.update(city)
                runOnUiThread { Toast.makeText(this, getString(R.string.saved_successfully), Toast.LENGTH_LONG).show() }
            }
        }
        cityAdapter = CityAdapter(forecastList, ViewHolderType.FORECAST)
        binding.forecastList.adapter = cityAdapter
        binding.forecastList.layoutManager = LinearLayoutManager(this)
    }

    private fun getForecast(cityId: Long?) {
        cityId?.let {
            Service
                    .getService()
                    .getForecast(cityId,  Settings.getMeter(), Settings.getLanguage())
                    .enqueue(object : Callback<BaseResponse<Forecast>> {
                        override fun onResponse(
                                call: Call<BaseResponse<Forecast>>,
                                response: Response<BaseResponse<Forecast>>
                        ) {
                            response.body()?.list?.let {
                                forecastList.clear()
                                forecastList.addAll(it)
                                cityAdapter.notifyDataSetChanged()
                                cityAdapter.filter.filter(Filter.NONE)
                            }
                        }

                        override fun onFailure(call: Call<BaseResponse<Forecast>>, t: Throwable) {
                            t.printStackTrace()
                        }

                    })
        }
    }

    companion object {
        const val CITY = "CITY"
    }
}