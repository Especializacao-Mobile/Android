package br.imaginefree.weather.di

import androidx.room.Room
import br.imaginefree.weather.data.local.AppDatabase
import br.imaginefree.weather.data.local.Settings
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
    single { Settings() }

}