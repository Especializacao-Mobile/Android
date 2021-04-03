package br.imaginefree.weather.data.local.interfaces

import androidx.room.*
import br.imaginefree.weather.data.model.Forecast
import br.imaginefree.weather.data.model.ForecastWithWeathers

@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(forecast: Forecast): Long

    @Update
    suspend fun update(forecast: Forecast)

    @Delete
    suspend fun delete(forecast: Forecast)

    @Transaction
    @Query("select * from forecast")
    suspend fun getForecasts(): List<ForecastWithWeathers>
    
}