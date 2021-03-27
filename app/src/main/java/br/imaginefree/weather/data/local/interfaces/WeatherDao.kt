package br.imaginefree.weather.data.local.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import br.imaginefree.weather.data.model.Weather

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(weather: Weather): Long

    @Update
    suspend fun update(weather: Weather)

    @Delete
    suspend fun delete(weather: Weather)
    
}