package br.imaginefree.weather.features.home

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import br.imaginefree.weather.R
import br.imaginefree.weather.data.local.prefs.Settings
import br.imaginefree.weather.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val settings: Settings by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        navController = Navigation.findNavController(this, R.id.nav_main_fragment)
        setUpBottomNavigation()
        settings.setConfigPreferencesInstance(this)
    }

    private fun setUpBottomNavigation() {
        binding.bottomNavigation?.apply {
            setOnNavigationItemSelectedListener(this@MainActivity)
        }
        binding.bottomNavigation.selectedItemId = R.id.search
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                navController.navigate(R.id.go_to_search)
                return true
            }
            R.id.favorites -> {
                navController.navigate(R.id.go_to_favorites)
                return true
            }
            R.id.settings -> {
                navController.navigate(R.id.go_to_settings)
                return true
            }
        }
        return false
    }
}