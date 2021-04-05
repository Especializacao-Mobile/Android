package br.imaginefree.city

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.imaginefree.city.databinding.ItemCityBinding

class CityAdapter(
    private val elements: ArrayList<City>,
    private val listener: (city: City) -> Unit
): RecyclerView.Adapter<CityViewHolder>() {

    private lateinit var binding: ItemCityBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(elements[position], listener)
    }

    override fun getItemCount() = elements.size

}