package br.imaginefree.weather.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cloud(
    var all: Double = 0.0
): Parcelable