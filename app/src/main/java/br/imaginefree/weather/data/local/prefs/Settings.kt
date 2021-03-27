package br.imaginefree.weather.data.local.prefs

import android.content.Context
import android.content.SharedPreferences

/**
 * units: metric para C° e imperial para F°
 * lang: PT ou EN
 */

class Settings {
    private val CONFIG_PREFERENCES = "CONFIG_PREFERENCES"
    val metric = "metric"
    val imperial = "imperial"
    val langPT = "PT"
    val langEN = "EN"
    val onlineMode = "onlineMode"

    private var configPreferences: SharedPreferences? = null

    fun setConfigPreferencesInstance(context: Context) {
        configPreferences =
            context.getSharedPreferences(CONFIG_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun getConfigPreferencesEditor(context: Context): SharedPreferences.Editor {
        return context.getSharedPreferences(CONFIG_PREFERENCES, Context.MODE_PRIVATE).edit()
    }

    fun isPT(): Boolean {
        return configPreferences?.getBoolean(langPT, false) ?: false
    }

    fun isEN(): Boolean {
        return configPreferences?.getBoolean(langEN, false) ?: false
    }

    fun isOnLine(): Boolean {
        return configPreferences?.getBoolean(onlineMode, false) ?: false
    }

    fun isMetric(): Boolean {
        return configPreferences?.getBoolean(metric, false) ?: false
    }

    fun isImperial(): Boolean {
        return configPreferences?.getBoolean(imperial, false) ?: false
    }

    fun getLanguage(): String {
        return when {
            isEN() -> {
                langEN
            }
            isPT() -> {
                langPT
            }
            else -> {
                langPT
            }
        }
    }

    fun getMeter(): String {
        return when {
            isMetric() -> {
                metric
            }
            isImperial() -> {
                imperial
            }
            else -> {
                metric
            }
        }
    }
}