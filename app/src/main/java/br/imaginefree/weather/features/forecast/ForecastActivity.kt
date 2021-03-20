package br.imaginefree.weather.features.forecast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import br.imaginefree.weather.R
import br.imaginefree.weather.data.model.BaseResponse
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.data.model.Forecast
import br.imaginefree.weather.data.network.Service
import br.imaginefree.weather.databinding.ActivityForecastBinding
import br.imaginefree.weather.features.adapter.AdapterType
import br.imaginefree.weather.features.adapter.CityAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForecastBinding
    private lateinit var cityAdapter: CityAdapter
    private val forecastList = ArrayList<Forecast>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val city = intent.extras?.get("CITY") as? City
        binding.place.text = city!!.name
        binding.temperature.text = city!!.main.temp.toString()
        getForecast(city?.cityId)
        binding.favorite

        cityAdapter = CityAdapter(forecastList, AdapterType.FORECAST)
        binding.forecastList.adapter = cityAdapter
        binding.forecastList.layoutManager = LinearLayoutManager(this)

    }

    private fun getForecast(cityId: Long?) {
        cityId?.let {
            Service
                    .getService()
                    .getForecast(cityId, "b02f5abb291a5a402a86d45e3807c357")
                    .enqueue(object : Callback<BaseResponse<Forecast>> {
                        override fun onResponse(
                                call: Call<BaseResponse<Forecast>>,
                                response: Response<BaseResponse<Forecast>>
                        ) {
                            response.body()?.list?.let {
                                forecastList.clear()
                                it.forEach {
                                    Log.d("Response:", it.toString())
                                }
                                forecastList.addAll(it)
                                cityAdapter.notifyDataSetChanged()
                            }
                        }

                        override fun onFailure(call: Call<BaseResponse<Forecast>>, t: Throwable) {
                            t.printStackTrace()
                        }

                    })
        }
    }
}