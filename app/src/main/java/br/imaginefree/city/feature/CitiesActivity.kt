package br.imaginefree.city.feature

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import br.imaginefree.city.bg.createChannelNotifications
import br.imaginefree.city.databinding.ActivityCitiesBinding
import br.imaginefree.city.feature.CityDetailsActivity.Companion.CITY
import br.imaginefree.city.feature.adapter.CityAdapter
import br.imaginefree.city.model.City
import br.imaginefree.city.util.initWorkManagers
import br.imaginefree.city.util.setUpDownloadImagesWorker
import br.imaginefree.city.util.setUpDownloadWorker

class CitiesActivity : AppCompatActivity() {

    private val cities = ArrayList<City>()
    private lateinit var binding: ActivityCitiesBinding
    private lateinit var cityAdapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpView()
        createChannelNotifications(this@CitiesActivity)
        setUpDownloadImagesWorker(this@CitiesActivity)
        setUpDownloadWorker(this@CitiesActivity, {
            cities.addAll(it)
            cityAdapter.notifyDataSetChanged()
            setListView()
        }, {
            setUpError()
        })
        initWorkManagers(this@CitiesActivity)
    }

    private fun setListView(){
        binding.progressCircular.isVisible = false
        binding.rvListCities.isVisible = true
        binding.errorLoading.isVisible = false
    }

    private fun setUpError(){
        binding.progressCircular.isVisible = false
        binding.rvListCities.isVisible = false
        binding.errorLoading.isVisible = true
    }

    private fun setUpView() {
        cityAdapter = CityAdapter(cities) {
            val i = Intent(this, CityDetailsActivity::class.java)
            i.putExtra(CITY, it)
            startActivity(i)
        }

        binding.rvListCities.layoutManager = LinearLayoutManager(this)
        binding.rvListCities.adapter = cityAdapter
    }


}