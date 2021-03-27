package br.imaginefree.weather.features.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.imaginefree.weather.R
import br.imaginefree.weather.databinding.FragmentSettingsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var binding: FragmentSettingsBinding
    private val settingsViewModel: SettingsViewModel by viewModel()

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
        setChangeListeners()
    }

    private fun setUpView() {
        binding.celsius.isChecked = settingsViewModel.isMetric()
        binding.fahrenheit.isChecked = settingsViewModel.isImperial()

        binding.offline.isChecked = !settingsViewModel.isOnLine()
        binding.online.isChecked = settingsViewModel.isOnLine()

        binding.pt.isChecked = settingsViewModel.isPT()
        binding.en.isChecked = settingsViewModel.isEN()

    }

    private fun setChangeListeners(){

        binding.degrees.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.celsius.id -> {
                    settingsViewModel.setMetric()
                }
                binding.fahrenheit.id -> {
                    settingsViewModel.setImperial()
                }
            }
        }

        binding.language.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.pt.id -> {
                    settingsViewModel.setPT()
                }
                binding.en.id -> {
                    settingsViewModel.setEN()
                }
            }
        }

        binding.internetMode.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.offline.id -> {
                    settingsViewModel.setOffLine()
                }
                binding.online.id -> {
                    settingsViewModel.setOnLine()
                }
            }
        }
    }

}