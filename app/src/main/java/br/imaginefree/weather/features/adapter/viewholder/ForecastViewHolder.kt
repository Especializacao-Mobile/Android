package br.imaginefree.weather.features.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import br.imaginefree.weather.data.model.Forecast
import br.imaginefree.weather.databinding.ItemForecastBinding
import br.imaginefree.weather.utils.Settings

class ForecastViewHolder (private val binding: ItemForecastBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(forecast: Forecast){
        binding.humidity.text = forecast.main.humidity.toString()
        binding.time.text = forecast.dt_txt
        if (forecast.weather.isNotEmpty()) {
            binding.sky.text = forecast.weather[0].description
        }
        if (Settings.getMeter() == Settings.METRIC){
            binding.temperatureType.text = "ºC"
        } else if (Settings.getMeter() == Settings.IMPERIAL){
            binding.temperatureType.text = "ºF"
        }
        binding.sky.text = forecast.weather[0].main
        binding.temperature.text = forecast.main.temp.toString()
        binding.wind.text = forecast.wind.speed.toString()
    }

}