package br.imaginefree.weather.data.repository.services

import br.imaginefree.weather.data.local.prefs.Settings
import br.imaginefree.weather.data.model.BaseModel
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.data.model.Status
import br.imaginefree.weather.data.repository.api.ApiService

class CityService(private val apiService: ApiService, private val settings: Settings) {

    suspend fun getCitiesByName(searchedName: String): BaseModel<List<City>> {

        lateinit var resultResponse: BaseModel<List<City>>

        val result = runCatching {
            val response = apiService.getCity(searchedName, settings.getMeter(), settings.getLanguage())
            resultResponse = if (response.isSuccessful) {
                response.body()?.let {
                    BaseModel(Status.SUCCESS, it.list)
                } ?: run {
                    BaseModel(Status.ERROR, null)
                }
            } else {
                BaseModel(Status.ERROR, null)
            }
        }

        return if (result.isFailure) {
            return BaseModel(Status.ERROR, null)
        } else {
            resultResponse
        }
    }

}