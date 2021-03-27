package br.imaginefree.weather.features.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.imaginefree.weather.R
import br.imaginefree.weather.data.local.Settings
import br.imaginefree.weather.databinding.FragmentSettingsBinding
import org.koin.android.ext.android.inject

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var binding: FragmentSettingsBinding
    private val settings: Settings by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    private fun setUpView() {
        binding.celsius.isChecked = settings.isMetric()
        binding.fahrenheit.isChecked = settings.isImperial()

        binding.offline.isChecked = !settings.isOnLine()
        binding.online.isChecked = settings.isOnLine()

        binding.pt.isChecked = settings.isPT()
        binding.en.isChecked = settings.isEN()

        binding.degrees.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.celsius.id -> {
                    setElement(settings.METRIC, settings.IMPERIAL)
                }
                binding.fahrenheit.id -> {
                    setElement(settings.IMPERIAL, settings.METRIC)
                }
            }
        }

        binding.language.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.pt.id -> {
                    setElement(settings.LANG_PT, settings.LANG_EN)
                }
                binding.en.id -> {
                    setElement(settings.LANG_EN, settings.LANG_PT)
                }
            }
        }

        binding.internetMode.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.offline.id -> {
                    setElement(null, settings.ONLINE_MODE)
                }
                binding.online.id -> {
                    setElement(settings.ONLINE_MODE, null)
                }
            }
        }
    }

    private fun setElement(keyOne: String? = null, keyTwo: String? = null){
        settings.getConfigPreferencesEditor(requireContext()).apply {
            keyOne?.let {
                putBoolean(it, true)
            }
            keyTwo?.let {
                putBoolean(it, false)
            }
            commit()
        }
    }

}