package br.imaginefree.weather.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.data.model.Weather

@Database(entities = [City::class, Weather::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
    abstract fun weatherDao(): WeatherDao
}
