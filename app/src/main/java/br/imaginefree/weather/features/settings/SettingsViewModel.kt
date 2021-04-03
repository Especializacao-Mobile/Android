package br.imaginefree.weather.features.settings

import android.content.SharedPreferences
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import br.imaginefree.weather.data.local.prefs.Settings

class SettingsViewModel(
        private val settings: Settings,
        private val editor: SharedPreferences.Editor
) : ViewModel(), LifecycleObserver {

    fun isPT(): Boolean {
        return settings.isPT()
    }

    fun setPT(){
        setElement(settings.langPT, settings.langEN)
    }

    fun isEN(): Boolean {
        return settings.isEN()
    }

    fun setEN(){
        setElement(settings.langEN, settings.langPT)
    }

    fun isOnLine(): Boolean {
        return settings.isOnLine()
    }

    fun setOnLine(){
        setElement(settings.onlineMode, null)
    }

    fun setOffLine(){
        setElement(null, settings.onlineMode)
    }

    fun isMetric(): Boolean {
        return settings.isMetric()
    }

    fun setMetric(){
        setElement(settings.metric, settings.imperial)
    }

    fun isImperial(): Boolean {
        return settings.isImperial()
    }

    fun setImperial(){
        setElement(settings.imperial, settings.metric)
    }

    private fun setElement(keyOne: String? = null, keyTwo: String? = null) {
        editor.apply {
            keyOne?.let {
                putBoolean(it, true)
            }
            keyTwo?.let {
                putBoolean(it, false)
            }
            commit()
        }
    }

}