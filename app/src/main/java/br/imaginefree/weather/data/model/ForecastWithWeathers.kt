package br.imaginefree.weather.data.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.android.parcel.Parcelize

@Parcelize
class ForecastWithWeathers(
    @Embedded var forecast: Forecast,
    @Relation(
        parentColumn = "dt",
        entityColumn = "weatherOwnerId"
    )
    var weather: List<Weather>
): Parcelable