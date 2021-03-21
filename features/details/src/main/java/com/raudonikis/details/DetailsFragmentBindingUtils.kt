package com.raudonikis.details

import android.content.Context
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.raudonikis.common.extensions.isLongerThan
import com.raudonikis.common.extensions.limit
import com.raudonikis.common.extensions.prefixHttps
import com.raudonikis.common.extensions.showIf
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
    bindGameDescription(context, game)
    bindGameGenres(context, game)
    bindGameItem(game)
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
        chip.setTextAppearance(R.style.TextAppearance_Body)
        chipsGenres.addView(chip)
    }
}

private fun FragmentDetailsBinding.bindGameItem(game: Game) {
    gameItem.bindGame(game)
}

private fun FragmentDetailsBinding.bindGameWallpaper(game: Game) {
    if (game.screenshots.isNotEmpty()) {
        Glide
            .with(root)
            .load(game.screenshots.first().url.prefixHttps())
            .placeholder(R.drawable.game_placeholder)
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

private fun FragmentDetailsBinding.bindGameDescription(context: Context, game: Game) {
    val maxCharacterCount = 120
    var isShowingMore = false
    textReadMore.setOnClickListener {
        textDescription.text = when (isShowingMore) {
            true -> {
                textReadMore.text = context.getString(R.string.action_read_more)
                isShowingMore = false
                game.description.limit(maxCharacterCount)
            }
            else -> {
                textReadMore.text = context.getString(R.string.action_read_less)
                isShowingMore = true
                game.description
            }
        }
    }
    textDescription.text = game.description.limit(maxCharacterCount)
    textReadMore.showIf { game.description.isLongerThan(maxCharacterCount) }
}