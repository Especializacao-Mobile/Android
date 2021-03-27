package br.imaginefree.weather.data.local.interfaces

import androidx.room.*
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.data.model.CityWithWeathers

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(city: City): Long

    @Update
    fun update(city: City)

    @Delete
    fun delete(city: City)

    @Transaction
    @Query("select * from city where name = :searchName")
    fun getCitiesByName(searchName: String): List<CityWithWeathers>

    @Transaction
    @Query("select * from city")
    fun getCities(): List<CityWithWeathers>

    @Transaction
    @Query("select * from city where favorite = 1")
    fun getFavoriteCities(): List<City>

}