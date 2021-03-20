package br.imaginefree.weather.features.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.databinding.ItemFavoriteBinding

class FavoriteAdapter(
    private val elements: ArrayList<City>,
    private val listener: () -> Unit
) : RecyclerView.Adapter<FavoriteViewHolder>() {

    private lateinit var binding: ItemFavoriteBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(elements[position], listener)
    }

    override fun getItemCount() = elements.size

}