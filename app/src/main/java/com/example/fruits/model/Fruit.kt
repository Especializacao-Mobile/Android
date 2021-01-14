package com.example.fruits.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fruit(
        var imageBase64: String? = null,
        var name: String? = null,
        var benefits: String? = null
) : Parcelable