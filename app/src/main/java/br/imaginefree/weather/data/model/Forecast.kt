package br.imaginefree.weather.data.model

data class Forecast(
    var dt: Long,
    var main: Main,
    var weather: List<Weather>,
    var clouds: Cloud,
    var wind: Wind,
    var sys: Sys,
    var dt_txt: String
)