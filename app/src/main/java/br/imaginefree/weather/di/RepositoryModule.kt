package br.imaginefree.weather.di

import br.imaginefree.weather.data.repository.services.CityService
import br.imaginefree.weather.data.repository.services.ForecastService
import org.koin.dsl.module

val repositoryModule = module {

    single {
        CityService(get(), get())
    }

    single {
        ForecastService(get(), get())
    }

}