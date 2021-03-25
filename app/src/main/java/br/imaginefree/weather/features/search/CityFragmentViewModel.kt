package br.imaginefree.weather.features.search

import android.content.Context
import androidx.lifecycle.*
import br.imaginefree.weather.data.local.AppDatabase
import br.imaginefree.weather.data.model.BaseModel
import br.imaginefree.weather.data.model.BaseResponse
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.data.model.STATUS
import br.imaginefree.weather.data.repository.CityService
import br.imaginefree.weather.utils.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CityFragmentViewModel : ViewModel(), LifecycleObserver {

    private val cityInfo = MutableLiveData<BaseModel<BaseResponse<City>>>()
    val cityInfoObservable: LiveData<BaseModel<BaseResponse<City>>>
        get() = cityInfo

    fun fetchCitiesByName(context: Context, searchName: String) {
        if (Settings.isOnLine()) {
            fetchByNameOnLine(context, searchName)
        } else {
            fetchByNameOffLine(context, searchName)
        }
    }

    private fun fetchByNameOnLine(context: Context, searchName: String) {
        cityInfo.postValue(BaseModel(STATUS.LOADING))
        viewModelScope.launch {
            val response = CityService().getCitiesByName(searchName)
            saveCities(context, response.data?.list)
            cityInfo.postValue(response)
        }
    }

    private fun fetchByNameOffLine(context: Context, searchName: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val list = arrayListOf<City>()
                val result =
                    AppDatabase.getInstance(context)?.cityDao()?.getCitiesByName(searchName)
                result?.forEach { citiesAndWeathers ->
                    citiesAndWeathers.city.weather = citiesAndWeathers.weather
                    list.add(citiesAndWeathers.city)
                }
                cityInfo.postValue(
                    BaseModel(
                        STATUS.SUCCESS,
                        BaseResponse("Ok", 200, list.size, list)
                    )
                )
            }
        }
    }

    private fun saveCities(context: Context, cities: List<City>?) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                cities?.forEach { city ->
                    AppDatabase.getInstance(context)?.cityDao()?.insert(city)
                    city.weather.forEach { weather ->
                        weather.weatherOwnerId = city.cityId
                        AppDatabase.getInstance(context)?.weatherDao()?.insert(weather)
                    }
                }
            }
        }
    }

}