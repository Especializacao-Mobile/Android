package br.imaginefree.weather.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import br.imaginefree.weather.data.local.interfaces.CityDao
import br.imaginefree.weather.data.local.interfaces.ForecastDao
import br.imaginefree.weather.data.local.interfaces.WeatherDao
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.data.model.Forecast
import br.imaginefree.weather.data.model.Weather

@Database(entities = [City::class, Weather::class, Forecast::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
    abstract fun weatherDao(): WeatherDao
    abstract fun forecastDao(): ForecastDao
}
