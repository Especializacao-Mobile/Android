package br.imaginefree.weather.features.favorite

import androidx.recyclerview.widget.RecyclerView
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.databinding.ItemFavoriteBinding

class FavoriteViewHolder(private val binding: ItemFavoriteBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(city: City, clickListener: () -> Unit){

    }

}