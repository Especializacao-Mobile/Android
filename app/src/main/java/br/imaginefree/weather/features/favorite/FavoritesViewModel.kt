package br.imaginefree.weather.features.favorite

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.imaginefree.weather.base.BaseModel
import br.imaginefree.weather.base.STATUS
import br.imaginefree.weather.data.local.services.CityDaoService
import br.imaginefree.weather.data.model.City
import kotlinx.coroutines.launch

class FavoritesViewModel(
        private val cityDaoService: CityDaoService
) : ViewModel(), LifecycleObserver {

    private val _cityToDeleted = MutableLiveData<BaseModel<City>>()
    val cityToDeleted: LiveData<BaseModel<City>> = _cityToDeleted

    private val _favoriteCities = MutableLiveData<BaseModel<List<City>>>()
    val favoriteCities: LiveData<BaseModel<List<City>>> = _favoriteCities

    fun removeFromFavorite(city: City) {
        _cityToDeleted.postValue(BaseModel(STATUS.LOADING))
        viewModelScope.launch {
            city.favorite = false
            cityDaoService.update(city)
            _cityToDeleted.postValue(BaseModel(STATUS.SUCCESS, city))
        }
    }

    fun fetchFavorites() {
        _favoriteCities.postValue(BaseModel(STATUS.LOADING))
        viewModelScope.launch {
            val cities = cityDaoService.getFavoriteCities()
            _favoriteCities.postValue(BaseModel(STATUS.SUCCESS, cities))
        }
    }

}