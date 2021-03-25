package br.imaginefree.weather.features.search

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.imaginefree.weather.data.model.BaseModel
import br.imaginefree.weather.data.model.BaseResponse
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.data.model.STATUS
import br.imaginefree.weather.data.repository.CityService
import kotlinx.coroutines.launch

class CityFragmentViewModel: ViewModel(), LifecycleObserver {

    private val cityInfo = MutableLiveData<BaseModel<BaseResponse<City>>>()
    val cityInfoObservable: LiveData<BaseModel<BaseResponse<City>>>
            get() = cityInfo

    fun fetchCitiesByName(searchName: String){
        cityInfo.postValue(BaseModel(STATUS.LOADING))
        viewModelScope.launch {
            val response = CityService().getCities()
            cityInfo.postValue(response)
        }
    }

}