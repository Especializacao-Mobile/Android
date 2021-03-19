package br.imaginefree.weather.data.local

import androidx.room.*
import br.imaginefree.weather.data.model.Weather

@Dao
interface WeatherDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(weather: Weather): Int

    @Update
    fun update(weather: Weather)

    @Delete
    fun delete(weather: Weather)


}