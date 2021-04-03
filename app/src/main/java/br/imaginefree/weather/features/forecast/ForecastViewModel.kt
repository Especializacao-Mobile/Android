package br.imaginefree.weather.features.forecast

import androidx.lifecycle.*
import br.imaginefree.weather.data.local.interfaces.CityDao
import br.imaginefree.weather.data.local.interfaces.ForecastDao
import br.imaginefree.weather.data.local.interfaces.WeatherDao
import br.imaginefree.weather.data.model.BaseModel
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.data.model.Forecast
import br.imaginefree.weather.data.model.Status
import br.imaginefree.weather.data.repository.services.ForecastService
import kotlinx.coroutines.launch

class ForecastViewModel(
    private val cityDao: CityDao,
    private val forecastService: ForecastService,
    private val forecastDao: ForecastDao,
    private val weatherDao: WeatherDao
): ViewModel(), LifecycleObserver {

    private val _savedCity = MutableLiveData<BaseModel<City>>()
    val savedCity: LiveData<BaseModel<City>> = _savedCity

    private val _forecastCities = MutableLiveData<BaseModel<List<Forecast>>>()
    val forecast: LiveData<BaseModel<List<Forecast>>> = _forecastCities

    fun saveFavorite(city: City){
        _savedCity.postValue(BaseModel(Status.LOADING))
        viewModelScope.launch {
                city.favorite = true
                cityDao.update(city)
                _savedCity.postValue(BaseModel(Status.SUCCESS, city))
        }
    }

    fun fetchForecast(cityId: Long){
        _forecastCities.postValue(BaseModel(Status.LOADING))
        viewModelScope.launch {
            val response = forecastService.getForecast(cityId)
            _forecastCities.postValue(response)
            response.data?.let { saveForecast(cityId, it) }
        }
    }

    fun saveForecast(cityId: Long, forecasts: List<Forecast>){
        viewModelScope.launch {
            forecasts.forEach{ forecast ->
                forecastDao.insert(forecast)
                forecast.weather.forEach{ weather ->
                    weather.weatherOwnerId = cityId
                    weather.forecastOwnerId = forecast.dt
                    weatherDao.insert(weather)
                }
            }
        }
    }

}