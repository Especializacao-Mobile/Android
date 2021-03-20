package br.imaginefree.weather.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Sys(
    val country: String?,
    val pod: String?
): Parcelable