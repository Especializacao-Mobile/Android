package com.example.galleryexercise.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.galleryexercise.viewholder.GalleyItemViewHolder
import com.example.galleryexercise.model.Image
import com.example.galleryexercise.databinding.GalleryItemBinding

class GalleryAdapter(private val items: List<Image>) :
    RecyclerView.Adapter<GalleyItemViewHolder>() {

    private lateinit var binding: GalleryItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleyItemViewHolder {
        binding = GalleryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleyItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleyItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

}