package com.raudonikis.discover

import app.cash.turbine.test
import com.raudonikis.common.Outcome
import com.raudonikis.common_testing.MainCoroutineRule
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game_search.GameSearchUseCase
import com.raudonikis.data_domain.news_feed.NewsFeedUseCase
import com.raudonikis.data_domain.popular_games.PopularGamesUseCase
import com.raudonikis.data_domain.user_follow.UserFollowUseCase
import com.raudonikis.data_domain.user_search.UserSearchUseCase
import com.raudonikis.discover.popular_games.PopularGamesState
import com.raudonikis.discover.search.GameSearchState
import com.raudonikis.navigation.NavigationDispatcher
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class DiscoverViewModelTest {

    private lateinit var sut: DiscoverViewModel

    @RelaxedMockK
    private lateinit var popularGamesUseCase: PopularGamesUseCase

    @RelaxedMockK
    private lateinit var gameSearchUseCase: GameSearchUseCase

    @RelaxedMockK
    private lateinit var navigationDispatcher: NavigationDispatcher

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        every { popularGamesUseCase.getPopularGames() } returns flowOf(Outcome.empty())
        every { gameSearchUseCase.getGameSearchResults() } returns flowOf(Outcome.empty())
        sut = DiscoverViewModel(
            navigationDispatcher,
            popularGamesUseCase,
            gameSearchUseCase,
        )
    }

    @Test
    fun `init, update popular games called`() {
        //Assemble
        //Act (init)
        //Assert
        coVerify { popularGamesUseCase.updatePopularGames() }
    }

    @Test
    fun `onGameClicked, navigate to details called`() {
        //Assemble
        val game = Game()
        //Act
        sut.onGameClicked(game)
        //Assert
        verify { navigationDispatcher.navigate(DiscoverRouter.discoverToDetails(game)) }
    }

    @Test
    fun `onSearchQueryCleared, searchQuery empty`() {
        //Assemble
        //Act
        sut.search("search-query")
        sut.onSearchQueryCleared()
        //Assert
        assertEquals(sut.searchQuery, "")
    }

    @Test
    fun `onSearchQueryCleared, clearSearchResults on use case called`() {
        //Act
        sut.onSearchQueryCleared()
        //Assert
        verify { gameSearchUseCase.clearSearchResults() }
    }

    @Test
    fun `gameSearchState, emits correct search states`() = runBlockingTest {
        //Act
        sut.gameSearchState.test {
            cancelAndIgnoreRemainingEvents()
        }
        sut.search("query")
        val searchState = sut.gameSearchState.value
        sut.onSearchQueryCleared()
        val searchDisabledState = sut.gameSearchState.value
        //Assert
        assertTrue(searchState is GameSearchState.GameSearch)
        assertTrue(searchDisabledState is GameSearchState.Disabled)
    }

    @Test
    fun `search, when blank, clears search results`() {
        //Assemble
        //Act
        sut.search("")
        //Assert
        verify { gameSearchUseCase.clearSearchResults() }
    }

    @Test
    fun `search, when blank, disables search state`() = runBlockingTest {
        //Assemble
        //Act
        sut.gameSearchState.test {
            cancelAndIgnoreRemainingEvents()
        }
        sut.search("")
        //Assert
        assertTrue(sut.gameSearchState.value is GameSearchState.Disabled)
    }

    @Test
    fun `search, when blank, enables discover state`() = runBlockingTest {
        //Assemble
        //Act
        sut.popularGamesState.test {
            cancelAndIgnoreRemainingEvents()
        }
        sut.search("")
        //Assert
        assertTrue(sut.popularGamesState.value is PopularGamesState.PopularGames)
    }

    @Test
    fun `search, when blank, does not call search on use case`() {
        //Assemble
        //Act
        sut.search("")
        //Assert
        coVerify(exactly = 0) { gameSearchUseCase.search(any()) }
    }

    @Test
    fun `search, when not blank, calls search on use case`() {
        //Assemble
        val query = "not blank"
        //Act
        sut.search(query)
        //Assert
        coVerify { gameSearchUseCase.search(query) }
    }

    @Test
    fun `search, when not blank, enables search state`() = runBlockingTest {
        //Assemble
        //Act
        sut.gameSearchState.test {
            cancelAndIgnoreRemainingEvents()
        }
        sut.search("not blank")
        //Assert
        assertTrue(sut.gameSearchState.value is GameSearchState.GameSearch)
    }
}