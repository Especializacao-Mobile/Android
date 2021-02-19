package com.example.galleryexercise.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.galleryexercise.databinding.GalleryItemBinding
import com.example.galleryexercise.model.Image

class GalleyItemViewHolder(private val binding: GalleryItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Image, listener: (image: Image) -> Unit) {
        binding.image.setImageURI(item.contentUri)
        binding.displayName.text = item.displayName
        binding.image.setOnClickListener {
            listener(item)
        }
    }
}