package br.imaginefree.weather.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Double,
    val humidity: Double
): Parcelable