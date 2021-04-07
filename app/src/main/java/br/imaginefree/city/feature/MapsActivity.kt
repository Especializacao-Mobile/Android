package br.imaginefree.city.feature

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import br.imaginefree.city.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val sp = LatLng(-23.533773, -46.625290)
        val bh = LatLng(-19.912998, -43.940933)
        val rec = LatLng(-8.05428, -34.8813)
        val rj = LatLng(-22.970722, -43.182365)
        val sal = LatLng(-12.9704, -38.5124)
        val foz = LatLng(-25.5469, -54.5882)

        mMap.addMarker(MarkerOptions().position(sp).title("Marker in São Paulo"))
        mMap.addMarker(MarkerOptions().position(bh).title("Marker in Belo Horizonte"))
        mMap.addMarker(MarkerOptions().position(rec).title("Marker in Recife"))
        mMap.addMarker(MarkerOptions().position(rj).title("Marker in Rio de Janeiro"))
        mMap.addMarker(MarkerOptions().position(sal).title("Marker in Salvador"))
        mMap.addMarker(MarkerOptions().position(foz).title("Marker in Foz do Iguaçu"))

        mMap.moveCamera(CameraUpdateFactory.newLatLng(rec))
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.list_cities -> {
            startActivity(Intent(this, CitiesActivity::class.java))
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

}