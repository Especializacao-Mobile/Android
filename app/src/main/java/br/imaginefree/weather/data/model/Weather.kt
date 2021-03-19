package br.imaginefree.weather.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class Weather(
    @SerializedName("id")
    @PrimaryKey
    val weatherId: Long,
    val weatherOwnerId: Long,
    val main: String,
    val description: String,
    val icon: String
)