package br.imaginefree.weather.features.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.data.model.Forecast
import br.imaginefree.weather.databinding.ItemCityBinding
import br.imaginefree.weather.databinding.ItemFavoriteBinding
import br.imaginefree.weather.databinding.ItemForecastBinding
import br.imaginefree.weather.features.adapter.filter.CityFilter
import br.imaginefree.weather.features.favorite.FavoriteViewHolder
import br.imaginefree.weather.features.search.CityViewHolder
import br.imaginefree.weather.features.forecast.ForecastViewHolder

class CityAdapter(
        private val elements: ArrayList<*>,
        private val adapterType: AdapterType,
        private val listener: ((Any) -> Unit)? = null,
): RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private lateinit var filter: CityFilter
    private val citiesFiltered: MutableList<City> = mutableListOf()

    init {
        citiesFiltered.addAll(citiesFiltered)
        (elements as? MutableList<City>)?.let { cities ->
            filter = CityFilter(citiesFiltered, this, cities)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (adapterType.name) {
            AdapterType.FAVORITE.name -> {
                val favoriteBinding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                FavoriteViewHolder(favoriteBinding)
            }
            AdapterType.FORECAST.name -> {
                val forecastBinding = ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ForecastViewHolder(forecastBinding)
            }
            else -> {
                val binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CityViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ForecastViewHolder)?.bind(elements[position] as Forecast)
        (holder as? CityViewHolder)?.bind(elements[position] as City, listener!!)
        (holder as? FavoriteViewHolder)?.bind(elements[position] as City, listener!!)
    }

    override fun getItemCount() = elements.size

    override fun getFilter(): Filter = filter

}