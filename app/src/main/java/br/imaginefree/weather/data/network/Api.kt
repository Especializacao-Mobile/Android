package br.imaginefree.weather.data.network

import br.imaginefree.weather.data.model.BaseResponse
import br.imaginefree.weather.data.model.City
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * units: metric para C° e imperial para F°
 * lang: PT ou EN
 */

interface Api {

    @GET("find")
    fun getCity(
        @Query("q") city: String,
        @Query("units") unit: String,
        @Query("lang") lang: String,
        @Query("appid") apiKey: String,
    ): Call<BaseResponse<City>>

    @GET("forecast")
    fun getForecast(
        @Query("id") id: Long,
        @Query("apiKey") apiKey: String,
    ): Call<BaseResponse<City>>

}