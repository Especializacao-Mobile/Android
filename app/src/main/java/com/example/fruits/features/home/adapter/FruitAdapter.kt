package com.example.fruits.features.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.fruits.databinding.FruitsItemBinding
import com.example.fruits.features.home.adapter.filter.FruitFilter
import com.example.fruits.features.home.viewholder.FruitViewHolder
import com.example.fruits.model.Fruit

class FruitAdapter(
    private val elements: ArrayList<Fruit>,
    private val listener: (fruit: Fruit) -> Unit
) : RecyclerView.Adapter<FruitViewHolder>(), Filterable {

    private lateinit var binding: FruitsItemBinding
    private var filter: FruitFilter
    private val fruitsFiltered: MutableList<Fruit> = mutableListOf()
    private var sortedList = false

    init {
        fruitsFiltered.addAll(elements)
        filter = FruitFilter(fruitsFiltered, sortedList, this, elements)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitViewHolder {
        binding = FruitsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FruitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FruitViewHolder, position: Int) {
        holder.bind(fruitsFiltered[position], listener)
    }

    fun isDistinctSize() = elements.size != fruitsFiltered.size

    fun isSortedAlphabetically() = filter.sortedList

    override fun getItemCount(): Int = fruitsFiltered.size

    override fun getFilter(): Filter = filter

}