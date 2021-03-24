package br.imaginefree.weather.features.adapter.filter

import android.widget.Filter
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.features.adapter.CityAdapter

class CityFilter(
    private val citiesFiltered: MutableList<City>,
    private val adapter: CityAdapter,
    private val elements: MutableList<City>
) : Filter() {

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        val results = FilterResults()
        results.values = getFilteredResults(constraint.toString())
        return results
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        adapter.notifyDataSetChanged()
    }

    private fun getFilteredResults(constraint: String): List<City> {
        citiesFiltered.clear()
        elements.forEach { city ->
            if (city.name.contains(constraint)) {
                citiesFiltered.add(city)
            }
        }
        return citiesFiltered
    }
}