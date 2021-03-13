package com.raudonikis.details

import android.content.Context
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.raudonikis.common.extensions.isLongerThan
import com.raudonikis.common.extensions.limit
import com.raudonikis.common.extensions.prefixHttps
import com.raudonikis.common.extensions.show
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game_release_date.GameDateUtils
import com.raudonikis.details.databinding.FragmentDetailsBinding
import com.raudonikis.details.game_screenshot.ScreenshotItem
import com.raudonikis.details.game_screenshot.mappers.ScreenshotItemMapper

fun FragmentDetailsBinding.bindGame(
    context: Context,
    game: Game,
    screenshotAdapter: ItemAdapter<ScreenshotItem>
) {
    bindGameTitle(game)
    bindGameReleaseDate(game)
    bindGameDescription(game)
    bindGameGenres(context, game)
    bindGameStatus(game)
    bindGameCover(game)
    bindGameWallpaper(game)
    bindGameScreenshots(game, screenshotAdapter)
}

private fun FragmentDetailsBinding.bindGameTitle(game: Game) {
    labelTitle.text = game.name
}

private fun FragmentDetailsBinding.bindGameReleaseDate(game: Game) {
    textReleaseDate.text = GameDateUtils.fromStringToFormattedString(game.releaseDate)
}

private fun FragmentDetailsBinding.bindGameGenres(context: Context, game: Game) {
    chipsGenres.removeAllViews()
    game.gameGenres.forEach { genre ->
        val chip =
            LayoutInflater.from(context).inflate(R.layout.chip_genre, chipsGenres, false) as Chip
        chip.text = genre.name
        chipsGenres.addView(chip)
    }
}

private fun FragmentDetailsBinding.bindGameStatus(game: Game) {
//    gameStatus.text = game.status.toString()
}

private fun FragmentDetailsBinding.bindGameCover(game: Game) {
    game.coverUrl?.let { url ->
        Glide
            .with(root)
            .load(url.prefixHttps())
            .centerCrop()
            .into(imageCover)
    }
}

private fun FragmentDetailsBinding.bindGameWallpaper(game: Game) {
    if (game.screenshots.isNotEmpty()) {
        Glide
            .with(root)
            .load(game.screenshots.first().url.prefixHttps())
            .centerCrop()
            .into(imageWallpaper)
    }
}

private fun FragmentDetailsBinding.bindGameScreenshots(
    game: Game,
    screenshotAdapter: ItemAdapter<ScreenshotItem>
) {
    screenshotAdapter.apply {
        clear()
        add(ScreenshotItemMapper.fromScreenshotList(game.screenshots))
    }
}

private fun FragmentDetailsBinding.bindGameDescription(game: Game) {
    //todo extract read more logic
    if (game.description.isLongerThan(100)) {
        textDescription.text = game.description.limit(100)
        textReadMore.show()
    } else {
        textDescription.text = game.description
    }
    textReadMore.setOnClickListener {
        textDescription.text = when (textDescription.text.toString().isLongerThan(103)) {
            true -> {
                textReadMore.text = "read more..."
                game.description.limit(100)
            }
            else -> {
                textReadMore.text = "read less..."
                game.description
            }
        }
    }
}