package com.raudonikis.data_domain.game_review.usecases

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.activity.cache.UserActivityDao
import com.raudonikis.data_domain.activity.mappers.UserActivityMapper
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game_deal.usecases.GameDealsUseCase
import com.raudonikis.data_domain.game_rating.GameRating
import com.raudonikis.data_domain.user.UserPreferences
import com.raudonikis.network.GamesApi
import com.raudonikis.network.auth.UserResponse
import com.raudonikis.network.game_review.GameReviewPostResponse
import com.raudonikis.network.utils.NetworkResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GameReviewUseCaseTest {

    private lateinit var gameReviewUseCase: GameReviewUseCase

    @RelaxedMockK
    private lateinit var gamesApi: GamesApi

    @RelaxedMockK
    private lateinit var userActivityDao: UserActivityDao

    @RelaxedMockK
    private lateinit var userPreferences: UserPreferences

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        gameReviewUseCase =
            GameReviewUseCase(gamesApi, userActivityDao, userPreferences, testDispatcher)
    }

    @After
    fun tearDown() {
        testDispatcher.cleanupTestCoroutines() // clear all
    }

    @Test
    fun `getGameReviewInfo, calls getGameReviewInfo from gamesApi`() =
        runBlockingTest {
            //Assemble
            val game = Game(name = "test-game")
            //Act
            gameReviewUseCase.getGameReviewInfo(game)
            //Assert
            coVerify { gamesApi.getGameReviewInfo(game.id) }
        }

    @Test
    fun `getGameReviewInfo, when gamesApi throws exception, returns failure`() =
        runBlockingTest {
            //Assemble
            coEvery { gamesApi.getGameReviewInfo(any()) } throws Exception("error")
            val expectedResult = Outcome.failure(message = "error")
            //Act
            val result = gameReviewUseCase.getGameReviewInfo(Game())
            //Assert
            Assert.assertEquals(expectedResult, result)
        }

    @Test
    fun `postReview, calls postReview from gamesApi`() =
        runBlockingTest {
            //Assemble
            val game = Game(name = "test-game")
            val rating = GameRating.DOWN_VOTED
            val comment = "test"
            //Act
            gameReviewUseCase.postReview(rating, comment, game)
            //Assert
            coVerify { gamesApi.postReview(any()) }
        }

    @Test
    fun `postReview, when gamesApi throws exception, returns failure`() =
        runBlockingTest {
            //Assemble
            val game = Game(name = "test-game")
            val rating = GameRating.DOWN_VOTED
            val comment = "test"
            coEvery { gamesApi.postReview(any()) } throws Exception("error")
            val expectedResult = Outcome.failure(message = "error")
            //Act
            val result = gameReviewUseCase.postReview(rating, comment, game)
            //Assert
            Assert.assertEquals(expectedResult, result)
        }

    @Test
    fun `postReview, onSuccess, adds new activity`() =
        runBlockingTest {
            //Assemble
            val game = Game(name = "test-game")
            val rating = GameRating.DOWN_VOTED
            val comment = "test"
            val gameReviewPostResponse = GameReviewPostResponse(
                0, null, true, "", "",
                UserResponse("", "", "", true)
            )
            coEvery { gamesApi.postReview(any()) } returns NetworkResponse(
                isSuccess = true,
                data = gameReviewPostResponse
            )
            //Act
            gameReviewUseCase.postReview(rating, comment, game)
            //Assert
            verify {
                userActivityDao.addNewMyActivity(
                    UserActivityMapper.onGameReviewUpdate(
                        gameReviewPostResponse,
                        game
                    )
                )
            }
        }

    @Test
    fun `postReview, not Success, does not add a new activity`() =
        runBlockingTest {
            //Assemble
            val game = Game(name = "test-game")
            val rating = GameRating.DOWN_VOTED
            val comment = "test"
            coEvery { gamesApi.postReview(any()) } returns NetworkResponse(
                isSuccess = false,
            )
            //Act
            gameReviewUseCase.postReview(rating, comment, game)
            //Assert
            verify(exactly = 0) { userActivityDao.addNewMyActivity(any()) }
        }
}