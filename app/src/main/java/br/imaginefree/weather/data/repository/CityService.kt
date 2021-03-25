package br.imaginefree.weather.data.repository

import br.imaginefree.weather.data.model.BaseModel
import br.imaginefree.weather.data.model.BaseResponse
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.data.model.STATUS
import br.imaginefree.weather.data.network.coroutines.Network
import br.imaginefree.weather.utils.Settings

class CityService {

    suspend fun getCitiesByName(searchedName: String): BaseModel<BaseResponse<City>> {

        lateinit var resultResponse: BaseModel<BaseResponse<City>>

        val result = runCatching {
            val response = Network.getService().getCity(searchedName, Settings.getMeter(), Settings.getLanguage())
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