package com.raudonikis.details

import androidx.core.net.toUri
import com.raudonikis.common.Outcome
import com.raudonikis.common_testing.MainCoroutineRule
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game.usecases.GameDetailsUseCase
import com.raudonikis.data_domain.game_deal.GameDeal
import com.raudonikis.data_domain.game_deal.GameDealShop
import com.raudonikis.data_domain.game_deal.usecases.GameDealsUseCase
import com.raudonikis.data_domain.game_review.usecases.GameReviewUseCase
import com.raudonikis.data_domain.game_search.GameSearchUseCase
import com.raudonikis.data_domain.game_status.GameStatus
import com.raudonikis.data_domain.game_status.usecases.GameStatusUseCase
import com.raudonikis.data_domain.popular_games.PopularGamesUseCase
import com.raudonikis.details.DetailsViewModel
import com.raudonikis.navigation.NavigationDispatcher
import com.raudonikis.network.game_status.GameStatusResponse
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailsViewModelTest {

    private lateinit var sut: DetailsViewModel

    @RelaxedMockK
    private lateinit var gameStatusUseCase: GameStatusUseCase

    @RelaxedMockK
    private lateinit var gameReviewUseCase: GameReviewUseCase

    @RelaxedMockK
    private lateinit var gameDetailsUseCase: GameDetailsUseCase

    @RelaxedMockK
    private lateinit var gameDealsUseCase: GameDealsUseCase

    @RelaxedMockK
    private lateinit var navigationDispatcher: NavigationDispatcher

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        sut = spyk(
            DetailsViewModel(
                navigationDispatcher,
                gameStatusUseCase,
                gameReviewUseCase,
                gameDetailsUseCase,
                gameDealsUseCase,
            )
        )
    }

    @Test
    fun `onCreate, updates current game`() {
        //Assemble
        val game = Game(name = "test-game")
        //Act
        sut.onCreate(game)
        //Assert
        assertEquals(game, sut.currentGame.value)
    }

    @Test
    fun `onGameUpdated, updates current game`() {
        //Assemble
        val game = Game(name = "test-game")
        //Act
        sut.onGameUpdated(game)
        //Assert
        assertEquals(game, sut.currentGame.value)
    }

    @Test
    fun `onCreate, updates game review info`() {
        //Assemble
        val game = Game(name = "test-game")
        //Act
        sut.onCreate(game)
        //Assert
        coVerify { gameReviewUseCase.getGameReviewInfo(game) }
    }

    @Test
    fun `onCreate, if update needed, updates game details`() {
        //Assemble
        val game = Game(name = "test-game", isUpdateNeeded = true)
        //Act
        sut.onCreate(game)
        //Assert
        coVerify { gameDetailsUseCase.getGameDetails(game) }
    }

    @Test
    fun `onCreate, if update not needed, does not update game details`() {
        //Assemble
        val game = Game(name = "test-game", isUpdateNeeded = false)
        //Act
        sut.onCreate(game)
        //Assert
        coVerify(exactly = 0) { gameDetailsUseCase.getGameDetails(game) }
    }

    @Test
    fun `updateGameStatus, if same status, does not call update on use case`() {
        //Assemble
        val gameStatus = GameStatus.WANT
        val game = Game(name = "test-game", status = gameStatus)
        //Act
        sut.onCreate(game)
        sut.updateGameStatus(gameStatus)
        //Assert
        coVerify(exactly = 0) { gameStatusUseCase.updateGameStatus(any()) }
    }

    @Test
    fun `updateGameStatus, if different status, calls update on use case`() {
        //Assemble
        val gameStatus = GameStatus.WANT
        val newGameStatus = GameStatus.PLAYED
        val game = Game(name = "test-game", status = gameStatus)
        //Act
        sut.onCreate(game)
        sut.updateGameStatus(newGameStatus)
        //Assert
        coVerify { gameStatusUseCase.updateGameStatus(game.copy(status = newGameStatus)) }
    }

    @Test
    fun `updateGameStatus, if success, updates current game`() {
        //Assemble
        coEvery { gameStatusUseCase.updateGameStatus(any()) } returns Outcome.success(
            GameStatusResponse("status", 1, "")
        )
        val gameStatus = GameStatus.WANT
        val newGameStatus = GameStatus.PLAYED
        val game = Game(name = "test-game", status = gameStatus)
        //Act
        sut.onCreate(game)
        sut.updateGameStatus(newGameStatus)
        //Assert
        verify { sut.onGameUpdated(game.copy(status = newGameStatus)) }
    }

    @Test
    fun `updateGameStatus, if failure, does not update current game`() {
        //Assemble
        coEvery { gameStatusUseCase.updateGameStatus(any()) } returns Outcome.failure()
        val gameStatus = GameStatus.WANT
        val newGameStatus = GameStatus.PLAYED
        val game = Game(name = "test-game", status = gameStatus)
        //Act
        sut.onCreate(game)
        sut.updateGameStatus(newGameStatus)
        //Assert
        verify(exactly = 0) { sut.onGameUpdated(game.copy(status = newGameStatus)) }
    }

    @Test
    fun `onDealsButtonClicked, calls search game deals on use case`() {
        //Act
        val currentGame = sut.currentGame.value
        sut.onDealsButtonClicked()
        //Assert
        coVerify { gameDealsUseCase.searchGameDeals(currentGame) }
    }

    @Test
    fun `onDealsButtonClicked, navigates to deals`() {
        //Act
        sut.onDealsButtonClicked()
        //Assert
        verify { navigationDispatcher.navigate(DetailsRouter.fromDetailsToDeals()) }
    }

    @Test
    fun `onReviewsButtonClicked, navigates to reviews`() {
        //Act
        val currentGame = sut.currentGame.value
        sut.onReviewsButtonClicked()
        //Assert
        verify { navigationDispatcher.navigate(DetailsRouter.fromDetailsToReviews(currentGame)) }
    }

    @Test
    fun `onGameStatusDeleteClicked, updates game status to EMPTY`() {
        //Act
        sut.onGameStatusDeleteClicked()
        //Assert
        verify { sut.updateGameStatus(GameStatus.EMPTY) }
    }

    @Test
    fun `onScreenshotClicked, navigates to screenshot`() {
        //Assemble
        val screenshotPosition = 1
        //Act
        sut.onScreenshotClicked(screenshotPosition)
        //Assert
        verify {
            navigationDispatcher.navigate(
                DetailsRouter.fromDetailsToScreenshot(
                    screenshotPosition
                )
            )
        }
    }

    @Test
    fun `onDealClicked, if url is null, does not navigate to deal uri`() {
        //Assemble
        val deal = GameDeal(1.1, 1.1, null, 1.1, GameDealShop("id", ""))
        //Act
        sut.onDealClicked(deal)
        //Assert
        verify(exactly = 0) { navigationDispatcher.navigate(uriString = any()) }
    }

    @Test
    fun `onDealClicked, if url is not null, navigates to deal uri`() {
        //Assemble
        val url = "url"
        val deal = GameDeal(1.1, 1.1, url, 1.1, GameDealShop("id", ""))
        //Act
        sut.onDealClicked(deal)
        //Assert
        verify { navigationDispatcher.navigate(uriString = url) }
    }
}