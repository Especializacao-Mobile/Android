package br.imaginefree.weather.features.search

import androidx.recyclerview.widget.RecyclerView
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.databinding.ItemCityBinding
import br.imaginefree.weather.databinding.ItemFavoriteBinding

class CityViewHolder(private val binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(city: City, clickListener: (City) -> Unit) {
        binding.humidity.text = city.main.humidity.toString()
        binding.place.text = city.name
        if (city.weather.isNotEmpty()) {
            binding.sky.text = city.weather[0].main
        }
        binding.temperature.text = city.main.temp.toString()
        binding.state.text = city.sys.country
        binding.wind.text = city.wind.speed.toString()
        binding.root.setOnClickListener {
            clickListener(city)
        }
    }

}