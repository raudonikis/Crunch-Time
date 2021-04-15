package com.raudonikis.details.full_image

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.raudonikis.common.extensions.prefixHttps
import com.raudonikis.details.DetailsViewModel
import com.raudonikis.details.R
import com.raudonikis.details.databinding.FragmentScreenshotBinding
import com.wada811.viewbinding.viewBinding

class ScreenshotFragment : Fragment(R.layout.fragment_screenshot) {

    private val viewModel: DetailsViewModel by hiltNavGraphViewModels(R.id.navigation_details)
    private val binding: FragmentScreenshotBinding by viewBinding()
    private val args: ScreenshotFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpScreenshot()
    }

    private fun setUpScreenshot() {
        viewModel.getScreenshotAtPosition(args.screenshotPosition)?.let { gameScreenshot ->
            Glide
                .with(this)
                .load(gameScreenshot.url.prefixHttps())
                .fitCenter()
                .into(binding.imageScreenshot)
        }
    }
}