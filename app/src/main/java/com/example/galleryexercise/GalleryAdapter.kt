package com.example.galleryexercise

import android.provider.MediaStore
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class GalleryAdapter(private val items: List<MediaStore.Images>) :
    RecyclerView.Adapter<GalleyItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleyItemViewHolder {

    }

    override fun onBindViewHolder(holder: GalleyItemViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() =items.size

}