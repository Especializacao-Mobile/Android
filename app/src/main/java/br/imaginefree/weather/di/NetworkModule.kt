package br.imaginefree.weather.di

import br.imaginefree.weather.BuildConfig
import br.imaginefree.weather.data.network.coroutines.Api
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val CONNECTION_TIMEOUT = 15L
private const val READ_TIMEOUT = 30L
const val PROPERTY_BASE_URL = "PROPERTY_BASE_URL"

val networkModule = module {

    single<OkHttpClient> {
        val httpLogInterceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            httpLogInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLogInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(httpLogInterceptor)
            .build()
    }

    single<Retrofit> {
        val baseUrl = getProperty<String>(PROPERTY_BASE_URL)
        Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(baseUrl)
            .client(get())
            .build()
    }

    single<Api> {
        val retrofit: Retrofit = get()
        retrofit.create<Api>(Api::class.java)
    }
}