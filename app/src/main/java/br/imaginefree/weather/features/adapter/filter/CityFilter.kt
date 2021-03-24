package br.imaginefree.weather.features.adapter.filter

import android.widget.Filter
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.features.adapter.CityAdapter

class CityFilter<T>(
    private val citiesFiltered: MutableList<City>,
    private val adapter: CityAdapter<T>,
    private val elements: MutableList<City>
) : Filter() {

    companion object {
        const val NONE = "NONE"
    }

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        val results = FilterResults()
        results.values = getFilteredResults(constraint.toString())
        return results
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        adapter.notifyDataSetChanged()
    }

    private fun getFilteredResults(constraint: String): List<City> {
        if (constraint == NONE){
            citiesFiltered.clear()
            citiesFiltered.addAll(elements)
        }else {
            citiesFiltered.clear()
            elements.forEach { city ->
                if (city.name.toLowerCase().contains(constraint.toLowerCase())) {
                    citiesFiltered.add(city)
                }
            }
        }
        return citiesFiltered
    }
}