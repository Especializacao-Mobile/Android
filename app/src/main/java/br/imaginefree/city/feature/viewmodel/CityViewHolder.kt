package br.imaginefree.city.feature.viewmodel

import androidx.recyclerview.widget.RecyclerView
import br.imaginefree.city.databinding.ItemCityBinding
import br.imaginefree.city.model.City

class CityViewHolder(private val binding: ItemCityBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(city: City, listener: (city: City) -> Unit){
        binding.cityName.text = city.local
        binding.cityPrice.text = city.preco
        binding.root.setOnClickListener {
            listener(city)
        }
    }


}