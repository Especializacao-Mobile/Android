package com.example.fruits.features.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fruits.databinding.FruitsItemBinding
import com.example.fruits.features.home.viewholder.FruitViewHolder
import com.example.fruits.model.Fruit

class FruitAdapter(
    private val elements: ArrayList<Fruit>,
    private val listener: (fruit: Fruit) -> Unit
) : RecyclerView.Adapter<FruitViewHolder>() {

    private lateinit var binding: FruitsItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitViewHolder {
        binding = FruitsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FruitViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: FruitViewHolder, position: Int) {
        holder.bind(elements[position], listener)
    }

    override fun getItemCount(): Int = elements.size

}