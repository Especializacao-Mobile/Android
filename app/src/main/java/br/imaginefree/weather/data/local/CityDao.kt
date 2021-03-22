package br.imaginefree.weather.data.local

import androidx.room.*
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.data.model.CityWithWeathers

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(city: City)

    @Update
    fun update(city: City)

    @Delete
    fun delete(city: City)

    @Transaction
    @Query("select * from city where name = :searchName")
    fun getCitiesByName(searchName: String): List<CityWithWeathers>

    @Transaction
    @Query("select * from city where favorite = 1")
    fun getFavoriteCities(): List<CityWithWeathers>

}