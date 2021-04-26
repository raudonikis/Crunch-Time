package com.raudonikis.details.game_screenshot.mappers

import com.raudonikis.data_domain.game_screenshot.GameScreenshot
import com.raudonikis.details.game_screenshot.ScreenshotItem

object ScreenshotItemMapper {

    fun fromScreenshot(screenshot: GameScreenshot): ScreenshotItem {
        return ScreenshotItem(screenshot)
    }

    fun fromScreenshotList(screenshots: List<GameScreenshot>): List<ScreenshotItem> {
        return screenshots.map { fromScreenshot(it) }
    }
}