package br.imaginefree.weather.data.repository.services

import br.imaginefree.weather.data.local.prefs.Settings
import br.imaginefree.weather.data.model.BaseModel
import br.imaginefree.weather.data.model.Forecast
import br.imaginefree.weather.data.model.Status
import br.imaginefree.weather.data.repository.api.ApiService

class ForecastService(private val apiService: ApiService, private val settings: Settings){

    suspend fun getForecast(cityId: Long): BaseModel<List<Forecast>> {

        lateinit var resultResponse: BaseModel<List<Forecast>>

        val result = runCatching {
            val response = apiService.getForecast(cityId,  settings.getMeter(), settings.getLanguage())
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