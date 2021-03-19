package br.imaginefree.weather.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.google.gson.annotations.SerializedName

@Entity
class City(
    @SerializedName("id")
    @PrimaryKey
    val cityId: Int,
    val dt: Long,
    val name: String,
    @Embedded val main: Main,
    @Embedded val wind: Wind,
    @Embedded val sys: Sys,
    @Embedded val clouds: Cloud,
    @Relation(
        parentColumn = "cityId",
        entityColumn = "weatherOwnerId"
    )
    val weather: List<Weather>
)