package com.example.galleryexercise.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.galleryexercise.model.Image
import com.example.galleryexercise.databinding.GalleryItemBinding

class GalleyItemViewHolder(private val binding: GalleryItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Image) {
        binding.image.setImageURI(item.contentUri)
        binding.displayName.text = item.displayName
    }
}