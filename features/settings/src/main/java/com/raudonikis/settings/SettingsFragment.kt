package com.raudonikis.settings

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.raudonikis.settings.databinding.FragmentSettingsBinding
import com.wada811.viewbinding.viewBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val binding: FragmentSettingsBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        setUpThemeSelection()
    }

    private fun setUpThemeSelection() {
        val items = resources.getStringArray(R.array.theme_selection)
        val adapter =
            ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, items)
        binding.autocompleteTheme.setAdapter(adapter)
        binding.autocompleteTheme.onItemSelectedListener =
            (object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val value = resources.getStringArray(R.array.theme_selection_values)[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            })
    }

    private fun setUpListeners() {
        binding.cardLogout.setOnClickListener {

        }
    }
}