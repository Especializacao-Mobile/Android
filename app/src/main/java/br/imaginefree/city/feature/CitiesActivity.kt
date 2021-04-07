package br.imaginefree.city.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.imaginefree.city.R
import br.imaginefree.city.bg.createChannelNotifications
import br.imaginefree.city.bg.initWorkManagers
import br.imaginefree.city.bg.setUpDownloadImagesWorker
import br.imaginefree.city.bg.setUpDownloadWorker
import br.imaginefree.city.model.City

class CitiesActivity : AppCompatActivity() {

    private val cities = ArrayList<City>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cities)
        createChannelNotifications(this@CitiesActivity)
        setUpDownloadImagesWorker(this@CitiesActivity)
        setUpDownloadWorker(this@CitiesActivity, cities)
        initWorkManagers(this@CitiesActivity)
    }
    

}