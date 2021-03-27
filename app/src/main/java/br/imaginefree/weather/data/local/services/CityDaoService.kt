package br.imaginefree.weather.data.local.services

import br.imaginefree.weather.data.local.interfaces.CityDao
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.data.model.CityWithWeathers

class CityDaoService(private val cityDao: CityDao) {
    
    suspend fun insert(city: City): Long {
        return cityDao.insert(city)
    }

    suspend fun update(city: City) {
        cityDao.update(city)
    }

    suspend fun delete(city: City) {
        cityDao.delete(city)
    }

    suspend fun getCitiesByName(searchName: String): List<CityWithWeathers> {
        return cityDao.getCitiesByName(searchName)
    }

    suspend fun getCities(): List<CityWithWeathers> {
        return cityDao.getCities()
    }

    suspend fun getFavoriteCities(): List<City> {
        return cityDao.getFavoriteCities()
    }
}