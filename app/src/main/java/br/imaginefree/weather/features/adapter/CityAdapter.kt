package br.imaginefree.weather.features.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.data.model.Forecast
import br.imaginefree.weather.databinding.ItemCityBinding
import br.imaginefree.weather.databinding.ItemForecastBinding
import br.imaginefree.weather.features.search.CityViewHolder
import br.imaginefree.weather.features.forecast.ForecastViewHolder

class CityAdapter(
        private val elements: ArrayList<*>,
        private val listener: ((Any) -> Unit)? = null,
        private val forecast: Boolean = false
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var binding: ItemCityBinding
    private lateinit var forecastBinding: ItemForecastBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(forecast){
            forecastBinding = ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ForecastViewHolder(forecastBinding)
        } else {
            binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CityViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ForecastViewHolder)?.bind(elements[position] as Forecast)
        (holder as? CityViewHolder)?.bind(elements[position] as City, listener!!)
    }

    override fun getItemCount() = elements.size

}