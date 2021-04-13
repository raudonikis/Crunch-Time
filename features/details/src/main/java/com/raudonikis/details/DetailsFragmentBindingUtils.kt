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
import com.raudonikis.data_domain.game_review.GameReviewInfo
import com.raudonikis.data_domain.game_status.GameStatus
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
    bindGameStatus(game)
    bindGameWallpaper(game)
    bindGameScreenshots(game, screenshotAdapter)
    bindGameReviewInfo(game.gameReviewInfo, context)
}

private fun FragmentDetailsBinding.bindGameStatus(game: Game) {
    when (game.status) {
        GameStatus.EMPTY -> {
            buttonChangeStatus.shrink()
        }
        else -> {
            buttonChangeStatus.text = game.status.toString()
            buttonChangeStatus.extend()
        }
    }
}

private fun FragmentDetailsBinding.bindGameReviewInfo(
    gameReviewInfo: GameReviewInfo?,
    context: Context
) {
    gameReviewInfo?.let {
        ratingLike.text = gameReviewInfo.positiveCount.toString()
        ratingDislike.text = gameReviewInfo.negativeCount.toString()
        val reviewCount = gameReviewInfo.negativeCount + gameReviewInfo.positiveCount
        buttonReviews.text = context.getString(R.string.button_reviews, reviewCount)
    }
}

private fun FragmentDetailsBinding.bindGameTitle(game: Game) {
    labelTitle.text = game.name
}

private fun FragmentDetailsBinding.bindGameReleaseDate(game: Game) {
    textReleaseDate.text = GameDateUtils.fromStringToFormattedString(game.releaseDate)
}

private fun FragmentDetailsBinding.bindGameGenres(context: Context, game: Game) {
    chipsGenres.removeAllViews()
    labelGenres.showIf { game.gameGenres.isNotEmpty() }
    chipsGenres.showIf { game.gameGenres.isNotEmpty() }
    game.gameGenres.map { genre ->
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
            .centerCrop()
            .into(imageWallpaper)
    }
}

private fun FragmentDetailsBinding.bindGameScreenshots(
    game: Game,
    screenshotAdapter: ItemAdapter<ScreenshotItem>
) {
    labelScreenshots.showIf { game.screenshots.isNotEmpty() }
    recyclerScreenshots.showIf { game.screenshots.isNotEmpty() }
    screenshotAdapter.apply {
        clear()
        add(ScreenshotItemMapper.fromScreenshotList(game.screenshots))
    }
}

private fun FragmentDetailsBinding.bindGameDescription(context: Context, game: Game) {
    textDescription.showIf { game.description.isNotBlank() }
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