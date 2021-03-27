package br.imaginefree.weather.features.search

import androidx.lifecycle.*
import br.imaginefree.weather.data.local.CityDao
import br.imaginefree.weather.base.BaseModel
import br.imaginefree.weather.base.BaseResponse
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.base.STATUS
import br.imaginefree.weather.data.local.CityDaoService
import br.imaginefree.weather.data.repository.CityService
import br.imaginefree.weather.data.local.Settings
import br.imaginefree.weather.data.local.WeatherDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CityViewModel(
        private val cityService: CityService,
        private val cityDao: CityDaoService,
        private val weatherDao: WeatherDao,
        private val settings: Settings
) : ViewModel(), LifecycleObserver {

    private val _cityInfo = MutableLiveData<BaseModel<BaseResponse<City>>>()
    val cityInfo: LiveData<BaseModel<BaseResponse<City>>> = _cityInfo

    fun fetchCitiesByName(searchName: String) {
        if (settings.isOnLine()) {
            fetchByNameOnLine(searchName)
        } else {
            fetchByNameOffLine(searchName)
        }
    }

    private fun fetchByNameOnLine(searchName: String) {
        _cityInfo.postValue(BaseModel(STATUS.LOADING))
        viewModelScope.launch {
            val response = cityService.getCitiesByName(searchName)
            saveCities(response.data?.list)
            _cityInfo.postValue(response)
        }
    }

    private fun fetchByNameOffLine(searchName: String) {
        viewModelScope.launch {
            val list = arrayListOf<City>()
            val result =
                    cityDao.getCitiesByName(searchName)
            result.forEach { citiesAndWeathers ->
                citiesAndWeathers.city.weather = citiesAndWeathers.weather
                list.add(citiesAndWeathers.city)
            }
            _cityInfo.postValue(
                    BaseModel(
                            STATUS.SUCCESS,
                            BaseResponse("Ok", 200, list.size, list)
                    )
            )
        }
    }

    private fun saveCities(cities: List<City>?) {
        viewModelScope.launch {
            cities?.forEach { city ->
                cityDao.insert(city)
                city.weather.forEach { weather ->
                    weather.weatherOwnerId = city.cityId
                    weatherDao.insert(weather)
                }
            }
        }
    }

}