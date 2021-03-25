package br.imaginefree.weather.di

import br.imaginefree.weather.features.search.CityFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { CityFragmentViewModel(get(), get(), get(), get()) }

}