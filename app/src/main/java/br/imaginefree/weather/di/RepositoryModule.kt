package br.imaginefree.weather.di

import br.imaginefree.weather.data.repository.CityService
import br.imaginefree.weather.data.repository.ForecastService
import org.koin.dsl.module

val repositoryModule = module {

    single {
        CityService(get(), get())
    }

    single {
        ForecastService(get(), get())
    }

}