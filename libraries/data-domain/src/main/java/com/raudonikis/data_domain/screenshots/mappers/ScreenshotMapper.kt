package com.raudonikis.data_domain.screenshots.mappers

import com.raudonikis.data_domain.screenshots.Screenshot
import com.raudonikis.network.game.GameScreenshotResponse

object ScreenshotMapper {

    fun fromScreenshotResponse(screenshotResponse: GameScreenshotResponse): Screenshot {
        return Screenshot(screenshotResponse.url)
    }

    fun fromScreenshotResponseList(screenshotResponses: List<GameScreenshotResponse>): List<Screenshot> {
        return screenshotResponses.map { fromScreenshotResponse(it) }
    }
}