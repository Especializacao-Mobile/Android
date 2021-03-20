package br.imaginefree.weather.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Wind(
    val speed: Double,
    val deg: Double
): Parcelable