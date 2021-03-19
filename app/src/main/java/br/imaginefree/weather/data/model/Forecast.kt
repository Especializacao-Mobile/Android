package br.imaginefree.weather.data.model

class Forecast(
    val dt: Long,
    val main: Main,
    val weather: Weather,
    val clouds: Cloud,
    val wind: Wind,
    val sys: Sys,
    val dt_txt: String
)