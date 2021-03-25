package br.imaginefree.weather.features.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.imaginefree.weather.R
import br.imaginefree.weather.data.local.AppDatabase
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.databinding.FragmentFavoritesBinding
import br.imaginefree.weather.features.adapter.AdapterType
import br.imaginefree.weather.features.adapter.CityAdapter
import br.imaginefree.weather.features.adapter.filter.Filter
import kotlin.concurrent.thread

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var cityAdapter: CityAdapter<City>
    private val cities = ArrayList<City>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        loadFavorites()
    }

    private fun setUpViews(){
        cityAdapter = CityAdapter(cities, AdapterType.FAVORITE){
            (it as? City)?.let { city ->
                city.favorite = false
                thread {
                    AppDatabase.getInstance(requireContext())?.cityDao()?.update(city)
                    val index = cities.indexOf(city)
                    cities.remove(city)
                    activity?.runOnUiThread { cityAdapter.notifyItemRemoved(index) }
                }
            }
        }
        binding.favoritesList.adapter = cityAdapter
        binding.favoritesList.layoutManager = LinearLayoutManager(requireContext())
        binding.btnSearch.setOnClickListener {
            if (binding.searchName.text.toString().isNullOrBlank()) {
                cityAdapter.filter.filter(Filter.NONE)
            }else{
                cityAdapter.filter.filter(binding.searchName.text.toString())
            }
        }
    }

    private fun loadFavorites(){
        thread {
            AppDatabase.getInstance(requireContext())?.cityDao()?.getFavoriteCities()?.let {
                cities.clear()
                cities.addAll(it)
                activity?.runOnUiThread {
                    cityAdapter.notifyDataSetChanged()
                    cityAdapter.filter.filter(Filter.NONE)
                }
            }
        }
    }

}