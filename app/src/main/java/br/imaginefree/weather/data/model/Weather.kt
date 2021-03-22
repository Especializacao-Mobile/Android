package br.imaginefree.weather.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Weather(
    @SerializedName("id")
    @PrimaryKey
    var weatherId: Int = 0,
    var weatherOwnerId: Int = 0,
    var main: String = "",
    var description: String = "",
    var icon: String = ""
): Parcelable