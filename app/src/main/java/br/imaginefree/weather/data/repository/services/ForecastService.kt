package br.imaginefree.weather.data.repository.services

import br.imaginefree.weather.base.BaseModel
import br.imaginefree.weather.base.BaseResponse
import br.imaginefree.weather.base.STATUS
import br.imaginefree.weather.data.local.prefs.Settings
import br.imaginefree.weather.data.model.Forecast
import br.imaginefree.weather.data.repository.api.ApiService

class ForecastService(private val apiService: ApiService, private val settings: Settings){

    suspend fun getForecast(cityId: Long): BaseModel<BaseResponse<Forecast>> {

        lateinit var resultResponse: BaseModel<BaseResponse<Forecast>>

        val result = runCatching {
            val response = apiService.getForecast(cityId,  settings.getMeter(), settings.getLanguage())
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