package br.imaginefree.weather.data.model

class BaseResponse<T> (
    val message: String,
    val cod: Int,
    val count: Int,
    val list: List<T>?,
    val city: City?
)