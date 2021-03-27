package br.imaginefree.weather.di

import androidx.room.Room
import br.imaginefree.weather.data.local.AppDatabase
import br.imaginefree.weather.data.local.prefs.Settings
import br.imaginefree.weather.data.local.services.CityDaoService
import br.imaginefree.weather.data.local.services.WeatherDaoService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "weather-db"
        ).build()
    }

    single { get<AppDatabase>().cityDao() }
    single { get<AppDatabase>().weatherDao() }
    single { CityDaoService(get()) }
    single { WeatherDaoService(get()) }
    single { Settings() }
    single { Settings().getConfigPreferencesEditor(androidContext()) }

}