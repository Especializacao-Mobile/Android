package br.imaginefree.city.feature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import br.imaginefree.city.databinding.ActivityCityDetailsBinding
import br.imaginefree.city.model.City
import java.io.File

class CityDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val city = intent.getParcelableExtra<City>(CITY)
        city?.let {
            binding.image.setImageURI(
                File(
                    this@CityDetailsActivity.filesDir,
                    city.imagem
                ).toUri()
            )
            binding.name.text = city.local
            binding.price.text = city.preco
        }
    }

    companion object {
        const val CITY = "CITY"
    }
}