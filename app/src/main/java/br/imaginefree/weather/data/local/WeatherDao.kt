package br.imaginefree.weather.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import br.imaginefree.weather.data.model.Weather

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weather: Weather): Long

    @Update
    fun update(weather: Weather)

    @Delete
    fun delete(weather: Weather)
    
}