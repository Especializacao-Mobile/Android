package br.imaginefree.weather.features.search

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.imaginefree.weather.data.model.City

class SearchAdapter(val elements: ArrayList<City>): RecyclerView.Adapter<SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = elements.size

}