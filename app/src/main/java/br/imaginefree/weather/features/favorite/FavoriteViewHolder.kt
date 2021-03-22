package br.imaginefree.weather.features.favorite

import androidx.recyclerview.widget.RecyclerView
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.databinding.ItemFavoriteBinding

class FavoriteViewHolder(private val binding: ItemFavoriteBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(city: City, clickListener: (City) -> Unit){
        binding.city.text = city.name
        binding.country.text = city.sys.country
        binding.delete.setOnClickListener {
            clickListener(city)
        }
    }

}