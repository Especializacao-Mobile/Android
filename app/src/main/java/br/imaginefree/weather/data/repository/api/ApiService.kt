package br.imaginefree.weather.data.repository.api

import br.imaginefree.weather.BuildConfig
import br.imaginefree.weather.data.repository.reponse.BaseResponse
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.data.model.Forecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("find")
    suspend fun getCity(
        @Query("q") city: String,
        @Query("units") unit: String,
        @Query("lang") lang: String,
        @Query("appid") apiKey: String = BuildConfig.PUBLIC_KEY
    ): Response<BaseResponse<City>>

    @GET("forecast")
    suspend fun getForecast(
        @Query("id") id: Long,
        @Query("units") unit: String,
        @Query("lang") lang: String,
        @Query("appid") apiKey: String = BuildConfig.PUBLIC_KEY
    ): Response<BaseResponse<Forecast>>

}