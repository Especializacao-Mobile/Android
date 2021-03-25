package br.imaginefree.weather.data.network

import br.imaginefree.weather.BuildConfig
import br.imaginefree.weather.data.model.BaseResponse
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.data.model.Forecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("find")
    fun getCity(
        @Query("q") city: String,
        @Query("units") unit: String,
        @Query("lang") lang: String,
        @Query("appid") apiKey: String = BuildConfig.PUBLIC_KEY
    ): Call<BaseResponse<City>>

    @GET("forecast")
    fun getForecast(
        @Query("id") id: Long,
        @Query("units") unit: String,
        @Query("lang") lang: String,
        @Query("appid") apiKey: String = BuildConfig.PUBLIC_KEY
    ): Call<BaseResponse<Forecast>>

}