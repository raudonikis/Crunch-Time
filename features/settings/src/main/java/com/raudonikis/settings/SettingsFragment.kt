package com.raudonikis.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.raudonikis.common.extensions.enableIf
import com.raudonikis.common_ui.extensions.observeInLifecycle
import com.raudonikis.common_ui.extensions.showShortSnackbar
import com.raudonikis.settings.app_theme.AppTheme
import com.raudonikis.settings.databinding.FragmentSettingsBinding
import com.raudonikis.settings.logout.LogoutEvent
import com.wada811.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val binding: FragmentSettingsBinding by viewBinding()
    private val viewModel: SettingsViewModel by viewModels()

    /**
     * Lifecycle hooks
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        setUpObservers()
        setUpThemeSelection()
    }

    /**
     * Set Up
     */
    private fun setUpListeners() {
        binding.cardLogout.setOnClickListener {
            viewModel.onLogoutClicked()
        }
    }

    private fun setUpObservers() {
        viewModel.logoutEvent
            .onEach { onLogoutEvent(it) }
            .observeInLifecycle(viewLifecycleOwner)
    }

    /**
     * Theme selection
     */
    private fun setUpThemeSelection() {
        val items = resources.getStringArray(R.array.theme_selection)
        binding.autocompleteTheme.apply {
            val selectedTheme = items[viewModel.getSelectedTheme().toPosition()]
            setText(selectedTheme, false)
            setAdapter(
                MaterialSpinnerAdapter(
                    requireContext(),
                    R.layout.support_simple_spinner_dropdown_item,
                    items
                )
            )
            setOnItemClickListener { _, _, position, _ ->
                viewModel.onThemeChanged(AppTheme.fromPosition(position))
            }
        }
    }

    /**
     * Logout
     */
    private fun onLogoutEvent(event: LogoutEvent) {
        binding.buttonLogout.enableIf { event != LogoutEvent.IN_PROGRESS }
        when (event) {
            LogoutEvent.IN_PROGRESS -> {

            }
            LogoutEvent.FAILURE -> {
                showShortSnackbar("Logout failed")
            }
            LogoutEvent.SUCCESS -> {
                showShortSnackbar("Logout success")
            }
        }
    }
}