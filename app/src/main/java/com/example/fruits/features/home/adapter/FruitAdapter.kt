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
    private var sortedList = false

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

    fun isDistinct() = elements.size != fruitsFiltered.size

    fun isSortedAlphabetically() = sortedList

    override fun getItemCount(): Int = fruitsFiltered.size

    override fun getFilter(): Filter {

        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()
                results.values = getFilteredResults(constraint.toString())
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                notifyDataSetChanged()
            }

            private fun getFilteredResults(constraint: String): List<Fruit> {
                if (constraint == MainActivity.NONE) {
                    fruitsFiltered.clear()
                    fruitsFiltered.addAll(elements)
                    sortedList = false
                } else if (constraint == MainActivity.SORTED_ALPHABETICALLY) {
                    fruitsFiltered.sortBy { it.name }
                    sortedList = true
                } else if (constraint == MainActivity.REMOVE_DUPLICATED) {
                    val newList = ArrayList<Fruit>()
                    newList.addAll(fruitsFiltered.distinct())
                    fruitsFiltered.clear()
                    fruitsFiltered.addAll(newList)
                }
                return fruitsFiltered
            }

            private fun fillAuxSearchable(){

            }
        }
    }

}