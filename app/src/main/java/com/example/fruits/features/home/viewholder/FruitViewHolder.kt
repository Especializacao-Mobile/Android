package com.example.fruits.features.home.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.fruits.databinding.FruitsItemBinding
import com.example.fruits.extensions.convertToBitmap
import com.example.fruits.model.Fruit

class FruitViewHolder(private val binding: FruitsItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(fruit: Fruit, listener: (fruit: Fruit) -> Unit) {
        binding.fruitImage.setImageBitmap(fruit.imageBase64?.convertToBitmap())
        binding.fruitName.text = fruit.name
        binding.fruitBenefits.text = fruit.benefits

        binding.root.setOnClickListener {
            listener(fruit)
        }
    }

}