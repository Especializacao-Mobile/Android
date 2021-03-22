package br.imaginefree.weather.data.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class City(
        @SerializedName("id")
        @PrimaryKey
        var cityId: Long = 0,
        var dt: Long = 0L,
        var name: String = "",
        @Embedded var main: Main = Main(),
        @Embedded var wind: Wind = Wind(),
        @Embedded var sys: Sys = Sys(),
        @Embedded var clouds: Cloud = Cloud(),
        @Ignore
        var weather: List<Weather> = ArrayList(),
        var favorite: Boolean = false
) : Parcelable