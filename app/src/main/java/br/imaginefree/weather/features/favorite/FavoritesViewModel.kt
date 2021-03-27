package br.imaginefree.weather.features.favorite

import androidx.lifecycle.*
import br.imaginefree.weather.base.BaseModel
import br.imaginefree.weather.base.STATUS
import br.imaginefree.weather.data.local.CityDao
import br.imaginefree.weather.data.model.City
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesViewModel(
    private val cityDao: CityDao
) : ViewModel(), LifecycleObserver {

    private val _cityToDeleted = MutableLiveData<BaseModel<City>>()
    val cityToDeleted: LiveData<BaseModel<City>> = _cityToDeleted

    private val _favoriteCities = MutableLiveData<BaseModel<List<City>>>()
    val favoriteCities: LiveData<BaseModel<List<City>>> = _favoriteCities

    fun removeFromFavorite(city: City){
        _cityToDeleted.postValue(BaseModel(STATUS.LOADING))
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                city.favorite = false
                cityDao.update(city)
                _cityToDeleted.postValue(BaseModel(STATUS.SUCCESS, city))
            }
        }
    }

    fun fetchFavorites(){
        _favoriteCities.postValue(BaseModel(STATUS.LOADING))
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val cities = cityDao.getFavoriteCities()
                _favoriteCities.postValue(BaseModel(STATUS.SUCCESS, cities))
            }
        }
    }

}