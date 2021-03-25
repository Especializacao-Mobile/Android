package br.imaginefree.weather.data.local

import android.content.Context
import android.content.SharedPreferences

/**
 * units: metric para C° e imperial para F°
 * lang: PT ou EN
 */

class Settings {
    private val CONFIG_PREFERENCES = "CONFIG_PREFERENCES"
    val METRIC = "metric"
    val IMPERIAL = "imperial"
    val LANG_PT = "PT"
    val LANG_EN = "EN"
    val ONLINE_MODE = "ONLINE_MODE"

    private var configPreferences: SharedPreferences? = null

    fun setConfigPreferencesInstance(context: Context) {
        configPreferences =
            context.getSharedPreferences(CONFIG_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun getConfigPreferencesEditor(context: Context): SharedPreferences.Editor {
        return context.getSharedPreferences(CONFIG_PREFERENCES, Context.MODE_PRIVATE).edit()
    }

    fun isPT(): Boolean {
        return configPreferences?.getBoolean(LANG_PT, false) ?: false
    }

    fun isEN(): Boolean {
        return configPreferences?.getBoolean(LANG_EN, false) ?: false
    }

    fun isOnLine(): Boolean {
        return configPreferences?.getBoolean(ONLINE_MODE, false) ?: false
    }

    fun isMetric(): Boolean {
        return configPreferences?.getBoolean(METRIC, false) ?: false
    }

    fun isImperial(): Boolean {
        return configPreferences?.getBoolean(IMPERIAL, false) ?: false
    }

    fun getLanguage(): String {
        return when {
            isEN() -> {
                LANG_EN
            }
            isPT() -> {
                LANG_PT
            }
            else -> {
                LANG_PT
            }
        }
    }

    fun getMeter(): String {
        return when {
            isMetric() -> {
                METRIC
            }
            isImperial() -> {
                IMPERIAL
            }
            else -> {
                METRIC
            }
        }
    }
}