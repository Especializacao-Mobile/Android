package br.imaginefree.weather.data.model

class BaseResponse<T> (
    val message: String? = null,
    val cod: Int? = null,
    val count: Int? = null,
    val list: List<T>,
    val city: City? = null
)