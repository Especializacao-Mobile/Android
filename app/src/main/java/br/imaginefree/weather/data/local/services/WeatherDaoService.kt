package br.imaginefree.weather.data.local.services

import br.imaginefree.weather.data.local.interfaces.WeatherDao
import br.imaginefree.weather.data.model.Weather

class WeatherDaoService(private val weatherDao: WeatherDao) {

    suspend fun insert(weather: Weather): Long {
        return weatherDao.insert(weather)
    }

    suspend fun update(weather: Weather) {
        weatherDao.update(weather)
    }

    suspend fun delete(weather: Weather) {
        weatherDao.delete(weather)
    }
}