package br.imaginefree.weather.data.repository.services

import br.imaginefree.weather.base.BaseModel
import br.imaginefree.weather.base.BaseResponse
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.base.STATUS
import br.imaginefree.weather.data.local.prefs.Settings
import br.imaginefree.weather.data.repository.api.ApiService

class CityService(private val apiService: ApiService, private val settings: Settings) {

    suspend fun getCitiesByName(searchedName: String): BaseModel<BaseResponse<City>> {

        lateinit var resultResponse: BaseModel<BaseResponse<City>>

        val result = runCatching {
            val response = apiService.getCity(searchedName, settings.getMeter(), settings.getLanguage())
            resultResponse = if (response.isSuccessful) {
                response.body()?.let {
                    BaseModel(STATUS.SUCCESS, it)
                } ?: run {
                    BaseModel(STATUS.ERROR, null)
                }
            } else {
                BaseModel(STATUS.ERROR, null)
            }
        }

        return if (result.isFailure) {
            return BaseModel(STATUS.ERROR, null)
        } else {
            resultResponse
        }
    }

}