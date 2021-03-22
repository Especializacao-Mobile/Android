package br.imaginefree.weather.data.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CityWithWeathers(
        @Embedded var city: City,
        @Relation(
                parentColumn = "cityId",
                entityColumn = "weatherOwnerId"
        )
        var weather: List<Weather>
): Parcelable