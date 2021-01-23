package com.example.fruits.features.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.fruits.databinding.FruitsItemBinding
import com.example.fruits.features.home.MainActivity
import com.example.fruits.features.home.viewholder.FruitViewHolder
import com.example.fruits.model.Fruit

class FruitAdapter(
    private val elements: ArrayList<Fruit>,
    private val listener: (fruit: Fruit) -> Unit
) : RecyclerView.Adapter<FruitViewHolder>(), Filterable {

    private lateinit var binding: FruitsItemBinding
    private val fruitsFiltered: MutableList<Fruit> = mutableListOf()

    init {
        fruitsFiltered.addAll(elements)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitViewHolder {
        binding = FruitsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FruitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FruitViewHolder, position: Int) {
        holder.bind(fruitsFiltered[position], listener)
    }

    override fun getItemCount(): Int = fruitsFiltered.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredResults: List<Fruit> = if (constraint == MainActivity.NONE) {
                    elements
                } else {
                    getFilteredResults(constraint.toString())
                }
                val results = FilterResults()
                results.values = filteredResults
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                fruitsFiltered.clear()
                results?.values?.let {
                    if (it is List<*> && it.isNotEmpty()) {
                        if (it[0] is Fruit){
                            fruitsFiltered.addAll(it as List<Fruit>)
                        }
                    }
                }
                notifyDataSetChanged()
            }

            private fun getFilteredResults(constraint: String): List<Fruit> {
                val results = ArrayList<Fruit>()
                if (constraint == MainActivity.SORTED_ALPHABETICALLY) {
                    results.addAll(elements)
                    results.sortBy { it.name }
                }
                if (constraint == MainActivity.REMOVE_DUPLICATED) {
                    results.addAll(elements.distinct())
                }
                return results
            }
        }
    }

}