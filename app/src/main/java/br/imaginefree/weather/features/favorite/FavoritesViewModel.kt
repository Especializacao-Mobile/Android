package br.imaginefree.weather.features.favorite

import androidx.lifecycle.*
import br.imaginefree.weather.data.local.interfaces.CityDao
import br.imaginefree.weather.data.model.BaseModel
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.data.model.Status
import kotlinx.coroutines.launch

class FavoritesViewModel(
        private val cityDao: CityDao
) : ViewModel(), LifecycleObserver {

    private val _cityToDeleted = MutableLiveData<BaseModel<City>>()
    val cityToDeleted: LiveData<BaseModel<City>> = _cityToDeleted

    private val _favoriteCities = MutableLiveData<BaseModel<List<City>>>()
    val favoriteCities: LiveData<BaseModel<List<City>>> = _favoriteCities

    fun removeFromFavorite(city: City) {
        _cityToDeleted.postValue(BaseModel(Status.LOADING))
        viewModelScope.launch {
            city.favorite = false
            cityDao.update(city)
            _cityToDeleted.postValue(BaseModel(Status.SUCCESS, city))
        }
    }

    fun fetchFavorites() {
        _favoriteCities.postValue(BaseModel(Status.LOADING))
        viewModelScope.launch {
            val cities = cityDao.getFavoriteCities()
            _favoriteCities.postValue(BaseModel(Status.SUCCESS, cities))
        }
    }

}