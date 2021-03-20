package br.imaginefree.weather.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Forecast(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Cloud,
    val wind: Wind,
    val sys: Sys,
    val dt_txt: String
): Parcelable