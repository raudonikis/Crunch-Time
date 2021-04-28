package com.raudonikis.details.game_review

import com.raudonikis.common.Outcome
import com.raudonikis.common_testing.MainCoroutineRule
import com.raudonikis.data_domain.game.mappers.GameMapper
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game_rating.GameRating
import com.raudonikis.data_domain.game_rating.GameRatingMapper
import com.raudonikis.data_domain.game_review.GameReviewInfo
import com.raudonikis.data_domain.game_review.mappers.GameReviewMapper
import com.raudonikis.data_domain.game_review.usecases.GameReviewUseCase
import com.raudonikis.network.auth.UserResponse
import com.raudonikis.network.game_review.GameReviewPostResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ReviewsViewModelTest {

    private lateinit var sut: ReviewsViewModel

    @RelaxedMockK
    private lateinit var gameReviewUseCase: GameReviewUseCase

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        sut = ReviewsViewModel(
            gameReviewUseCase,
        )
    }

    @Test
    fun `onCreate, sets current game to new value`() {
        //Assemble
        val game = Game(name = "test-game")
        //Act
        sut.onCreate(game)
        //Assert
        assertEquals(game, sut.currentGame.value)
    }

    @Test
    fun `postReview, if review already present, emit ALREADY_PRESENT event`() {
        //Assemble
        val game = Game(gameReviewInfo = GameReviewInfo(isReviewPresent = true))
        val rating = GameRating.DOWN_VOTED
        val comment = ""
        //Act
        sut.onCreate(game)
        sut.postReview(rating, comment)
        //Assert
        assertEquals(ReviewState.ALREADY_PRESENT, sut.writeReviewState.value)
    }

    @Test
    fun `postReview, if review already present, do not call postReview from use case`() {
        //Assemble
        val game = Game(gameReviewInfo = GameReviewInfo(isReviewPresent = true))
        val rating = GameRating.DOWN_VOTED
        val comment = ""
        //Act
        sut.onCreate(game)
        sut.postReview(rating, comment)
        //Assert
        coVerify(exactly = 0) { gameReviewUseCase.postReview(any(), any(), any()) }
    }

    @Test
    fun `postReview, calls postReview from use case`() {
        //Assemble
        val game = Game()
        val rating = GameRating.DOWN_VOTED
        val comment = ""
        //Act
        sut.onCreate(game)
        sut.postReview(rating, comment)
        //Assert
        coVerify { gameReviewUseCase.postReview(rating, comment, game) }
    }

    @Test
    fun `postReview, onSuccess, reviewState SUCCESS`() {
        //Assemble
        val gameReviewPostResponse =
            GameReviewPostResponse(
                0, "", false, "", "",
                UserResponse("", "", "", false)
            )
        coEvery { gameReviewUseCase.postReview(any(), any(), any()) } returns Outcome.success(
            gameReviewPostResponse
        )
        val rating = GameRating.DOWN_VOTED
        val comment = ""
        //Act
        sut.postReview(rating, comment)
        //Assert
        assertEquals(ReviewState.SUCCESS, sut.writeReviewState.value)
    }

    @Test
    fun `postReview, onSuccess, currentGame updated`() {
        //Assemble
        val gameReviewPostResponse =
            GameReviewPostResponse(
                0, "", false, "", "",
                UserResponse("", "", "", false)
            )
        coEvery { gameReviewUseCase.postReview(any(), any(), any()) } returns Outcome.success(
            gameReviewPostResponse
        )
        val rating = GameRating.DOWN_VOTED
        val comment = ""
        val gameReview = GameReviewMapper.fromGameReviewPostResponse(gameReviewPostResponse)
        val expectedGameResponse = GameMapper.addGameReview(sut.currentGame.value, gameReview)
        //Act
        sut.postReview(rating, comment)
        //Assert
        assertEquals(expectedGameResponse, sut.currentGame.value)
    }

    @Test
    fun `postReview, onFailure, reviewState FAILURE`() {
        //Assemble
        coEvery { gameReviewUseCase.postReview(any(), any(), any()) } returns Outcome.failure()
        val rating = GameRating.DOWN_VOTED
        val comment = ""
        //Act
        sut.postReview(rating, comment)
        //Assert
        assertEquals(ReviewState.FAILURE, sut.writeReviewState.value)
    }
}