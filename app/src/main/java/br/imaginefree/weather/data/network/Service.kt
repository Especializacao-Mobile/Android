package br.imaginefree.weather.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Service {
    private val instance = Retrofit.Builder()
        .baseUrl("http://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getService(): Api = instance.create(Api::class.java)
}