package br.imaginefree.weather.data.model

data class BaseModel<T>(
    val status: STATUS,
    var data: T? = null,
    val message: String? = null
)

enum class STATUS {
    LOADING, SUCCESS, ERROR
}

sealed class ViewState

class Loading : ViewState()
data class Success<T>(val data: BaseResponse<T>) : ViewState()
class Error : ViewState()