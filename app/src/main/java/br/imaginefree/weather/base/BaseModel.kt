package br.imaginefree.weather.base

data class BaseModel<T>(
    val status: STATUS,
    var data: T? = null,
    val message: String? = null
)

enum class STATUS {
    LOADING, SUCCESS, ERROR
}

/**
 * Another approach
 */
/*sealed class ViewState{
    object Loading : ViewState()
    data class Success<T>(val data: BaseResponse<T>) : ViewState()
    object Error : ViewState()
}*/

