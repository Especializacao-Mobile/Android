package br.imaginefree.weather.features.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.imaginefree.weather.R
import br.imaginefree.weather.data.model.City
import br.imaginefree.weather.databinding.FragmentFavoritesBinding
import br.imaginefree.weather.features.adapter.CityAdapter
import br.imaginefree.weather.features.adapter.viewholder.ViewHolderType
import br.imaginefree.weather.features.adapter.filter.Filter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var cityAdapter: CityAdapter<City>
    private val cities = ArrayList<City>()
    private val favoritesViewModel: FavoritesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setObservables()
        favoritesViewModel.fetchFavorites()
    }

    private fun setObservables(){
        favoritesViewModel.cityToDeleted.observe(viewLifecycleOwner, Observer { model ->
            model.data?.let {
                val index = cities.indexOf(it)
                cities.remove(it)
                cityAdapter.notifyItemRemoved(index)
            }
        })

        favoritesViewModel.favoriteCities.observe(viewLifecycleOwner, Observer { model ->
            model.data?.let {
                cities.clear()
                cities.addAll(it)
                cityAdapter.notifyDataSetChanged()
                cityAdapter.filter.filter(Filter.NONE)
            }
        })
    }

    private fun setUpViews(){
        cityAdapter = CityAdapter(cities, ViewHolderType.FAVORITE){
            (it as? City)?.let { city ->
                favoritesViewModel.removeFromFavorite(city)
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

}