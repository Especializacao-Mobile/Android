package br.imaginefree.weather.data.local.interfaces

import androidx.room.*
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.data.model.CityWithWeathers

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(city: City): Long

    @Update
    suspend fun update(city: City)

    @Delete
    suspend fun delete(city: City)

    @Transaction
    @Query("select * from city where name = :searchName")
    suspend fun getCitiesByName(searchName: String): List<CityWithWeathers>

    @Transaction
    @Query("select * from city")
    suspend fun getCities(): List<CityWithWeathers>

    @Transaction
    @Query("select * from city where favorite = 1")
    suspend fun getFavoriteCities(): List<City>

}