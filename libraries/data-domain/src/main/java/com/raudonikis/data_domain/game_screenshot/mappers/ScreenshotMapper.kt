package com.raudonikis.data_domain.game_screenshot.mappers

import com.raudonikis.data_domain.game_screenshot.Screenshot
import com.raudonikis.network.game_screenshot.GameScreenshotResponse

object ScreenshotMapper {

    fun fromScreenshotResponse(screenshotResponse: GameScreenshotResponse): Screenshot {
        return Screenshot(screenshotResponse.url)
    }

    fun fromScreenshotResponseList(screenshotResponses: List<GameScreenshotResponse>?): List<Screenshot> {
        return screenshotResponses?.map { fromScreenshotResponse(it) } ?: emptyList()
    }
}