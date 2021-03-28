package br.imaginefree.weather.data.model

data class BaseModel<T>(
    val status: Status,
    var data: T? = null,
    val message: String? = null
)

/**
 * Another approach
 */
/*sealed class ViewState{
    object Loading : ViewState()
    data class Success<T>(val data: BaseResponse<T>) : ViewState()
    object Error : ViewState()
}*/

