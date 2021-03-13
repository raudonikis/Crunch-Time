package com.raudonikis.details.screenshots.mappers

import com.raudonikis.data_domain.screenshots.Screenshot
import com.raudonikis.details.screenshots.ScreenshotItem

object ScreenshotItemMapper {

    fun fromScreenshot(screenshot: Screenshot): ScreenshotItem {
        return ScreenshotItem(screenshot)
    }

    fun fromScreenshotList(screenshots: List<Screenshot>): List<ScreenshotItem> {
        return screenshots.map { fromScreenshot(it) }
    }
}