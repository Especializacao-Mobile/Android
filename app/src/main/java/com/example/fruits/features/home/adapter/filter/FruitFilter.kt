package com.example.fruits.features.home.adapter.filter

import android.widget.Filter
import com.example.fruits.features.home.MainActivity
import com.example.fruits.features.home.adapter.FruitAdapter
import com.example.fruits.model.Fruit

class FruitFilter(
    private val fruitsFiltered: MutableList<Fruit>,
    var sortedList: Boolean, private val adapter: FruitAdapter,
    private val elements: MutableList<Fruit>
) : Filter() {

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        val results = FilterResults()
        results.values = getFilteredResults(constraint.toString())
        return results
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        adapter.notifyDataSetChanged()
    }

    private fun getFilteredResults(constraint: String): List<Fruit> {
        if (constraint == MainActivity.NONE) {
            fruitsFiltered.clear()
            fruitsFiltered.addAll(elements)
            sortedList = false
        }
        if (constraint == MainActivity.SORTED_ALPHABETICALLY) {
            fruitsFiltered.sortBy { it.name }
            sortedList = true
        }
        if (constraint == MainActivity.REMOVE_DUPLICATED) {
            val newList = ArrayList<Fruit>()
            newList.addAll(fruitsFiltered.distinct())
            fruitsFiltered.clear()
            fruitsFiltered.addAll(newList)
        }
        return fruitsFiltered
    }
}