package br.imaginefree.weather.data.repository.reponse

import br.imaginefree.weather.data.model.City

class BaseResponse<T> (
    val message: String? = null,
    val cod: Int? = null,
    val count: Int? = null,
    val list: List<T>,
    val city: City? = null
)