package br.imaginefree.weather.di

import br.imaginefree.weather.data.local.prefs.Settings
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val prefsModule = module {

    single { Settings() }
    single { get<Settings>().getConfigPreferencesEditor(androidContext()) }

}