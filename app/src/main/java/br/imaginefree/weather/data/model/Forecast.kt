package br.imaginefree.weather.data.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Forecast(
    @PrimaryKey
    var dt: Long = 0,
    @Embedded var main: Main = Main(),
    @Embedded var clouds: Cloud = Cloud(),
    @Embedded var wind: Wind = Wind(),
    @Embedded var sys: Sys = Sys(),
    @Ignore
    var weather: List<Weather> = ArrayList(),
    var dt_txt: String = ""
): Parcelable