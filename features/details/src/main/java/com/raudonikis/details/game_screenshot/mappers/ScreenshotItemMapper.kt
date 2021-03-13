package com.raudonikis.details.game_screenshot.mappers

import com.raudonikis.data_domain.game_screenshot.Screenshot
import com.raudonikis.details.game_screenshot.ScreenshotItem

object ScreenshotItemMapper {

    fun fromScreenshot(screenshot: Screenshot): ScreenshotItem {
        return ScreenshotItem(screenshot)
    }

    fun fromScreenshotList(screenshots: List<Screenshot>): List<ScreenshotItem> {
        return screenshots.map { fromScreenshot(it) }
    }
}