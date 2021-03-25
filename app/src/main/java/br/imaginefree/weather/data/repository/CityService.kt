package br.imaginefree.weather.data.repository

import br.imaginefree.weather.data.model.BaseModel
import br.imaginefree.weather.data.model.BaseResponse
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.data.model.STATUS
import br.imaginefree.weather.data.local.Settings
import br.imaginefree.weather.data.network.coroutines.Api

class CityService(private val api: Api, private val settings: Settings) {

    suspend fun getCitiesByName(searchedName: String): BaseModel<BaseResponse<City>> {

        lateinit var resultResponse: BaseModel<BaseResponse<City>>

        val result = runCatching {
            val response = api.getCity(searchedName, settings.getMeter(), settings.getLanguage())
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