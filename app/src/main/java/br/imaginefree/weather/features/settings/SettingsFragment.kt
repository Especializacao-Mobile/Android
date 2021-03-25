package br.imaginefree.weather.features.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.imaginefree.weather.R
import br.imaginefree.weather.databinding.FragmentSettingsBinding
import br.imaginefree.weather.utils.Settings.IMPERIAL
import br.imaginefree.weather.utils.Settings.LANG_EN
import br.imaginefree.weather.utils.Settings.LANG_PT
import br.imaginefree.weather.utils.Settings.METRIC
import br.imaginefree.weather.utils.Settings.ONLINE_MODE
import br.imaginefree.weather.utils.Settings.getConfigPreferencesEditor
import br.imaginefree.weather.utils.Settings.isEN
import br.imaginefree.weather.utils.Settings.isImperial
import br.imaginefree.weather.utils.Settings.isMetric
import br.imaginefree.weather.utils.Settings.isOnLine
import br.imaginefree.weather.utils.Settings.isPT

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    private fun setUpView() {
        binding.celsius.isChecked = isMetric()
        binding.fahrenheit.isChecked = isImperial()

        binding.offline.isChecked = !isOnLine()
        binding.online.isChecked = isOnLine()

        binding.pt.isChecked = isPT()
        binding.en.isChecked = isEN()

        binding.degrees.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.celsius.id -> {
                    setElement(METRIC, IMPERIAL)
                }
                binding.fahrenheit.id -> {
                    setElement(IMPERIAL, METRIC)
                }
            }
        }

        binding.language.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.pt.id -> {
                    setElement(LANG_PT, LANG_EN)
                }
                binding.en.id -> {
                    setElement(LANG_EN, LANG_PT)
                }
            }
        }

        binding.internetMode.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.offline.id -> {
                    setElement(null, ONLINE_MODE)
                }
                binding.online.id -> {
                    setElement(ONLINE_MODE, null)
                }
            }
        }
    }

    private fun setElement(keyOne: String? = null, keyTwo: String? = null){
        getConfigPreferencesEditor(requireContext()).apply {
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