package br.imaginefree.weather.base

import android.app.Application
import br.imaginefree.weather.BuildConfig
import br.imaginefree.weather.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppWeather : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppWeather)
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule,
                    networkModule,
                    databaseModule,
                    prefsModule
                )
            )
            properties(mapOf(PROPERTY_BASE_URL to BuildConfig.BASE_URL))
        }
    }

}