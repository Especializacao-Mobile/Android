package br.imaginefree.weather.data.repository

import br.imaginefree.weather.data.model.BaseModel
import br.imaginefree.weather.data.model.BaseResponse
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.data.model.STATUS
import br.imaginefree.weather.data.network.coroutines.Network

class CityService {

    suspend fun getCities(): BaseModel<BaseResponse<City>> {

        lateinit var resultResponse: BaseModel<BaseResponse<City>>

        val result = runCatching {
            val response = Network.getService().getCity("", "", "")
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