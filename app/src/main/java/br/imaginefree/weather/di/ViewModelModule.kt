package br.imaginefree.weather.di

import br.imaginefree.weather.features.favorite.FavoritesViewModel
import br.imaginefree.weather.features.forecast.ForecastViewModel
import br.imaginefree.weather.features.search.CityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { CityViewModel(get(), get(), get(), get()) }
    viewModel { FavoritesViewModel(get()) }
    viewModel { ForecastViewModel(get(), get()) }

}