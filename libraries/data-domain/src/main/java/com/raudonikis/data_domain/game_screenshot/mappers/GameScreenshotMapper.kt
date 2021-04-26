package com.raudonikis.data_domain.game_screenshot.mappers

import com.raudonikis.data_domain.game_screenshot.GameScreenshot
import com.raudonikis.network.game_screenshot.GameScreenshotResponse

object GameScreenshotMapper {

    fun fromScreenshotResponse(screenshotResponse: GameScreenshotResponse): GameScreenshot {
        return GameScreenshot(screenshotResponse.url)
    }

    fun fromScreenshotResponseList(screenshotResponses: List<GameScreenshotResponse>?): List<GameScreenshot> {
        return screenshotResponses?.map { fromScreenshotResponse(it) } ?: emptyList()
    }
}