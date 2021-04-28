package com.raudonikis.activity

import app.cash.turbine.test
import com.raudonikis.activity.news_feed.NewsFeedState
import com.raudonikis.activity.user_search.UserSearchState
import com.raudonikis.common.Outcome
import com.raudonikis.common_testing.MainCoroutineRule
import com.raudonikis.common_ui.follow.UserFollowEvent
import com.raudonikis.common_ui.follow.UserUnfollowEvent
import com.raudonikis.data_domain.news_feed.NewsFeedUseCase
import com.raudonikis.data_domain.user.User
import com.raudonikis.data_domain.user_follow.UserFollowUseCase
import com.raudonikis.data_domain.user_search.UserSearchUseCase
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class UserActivityViewModelTest {

    private lateinit var sut: UserActivityViewModel

    @RelaxedMockK
    private lateinit var newsFeedUseCase: NewsFeedUseCase

    @RelaxedMockK
    private lateinit var userSearchUseCase: UserSearchUseCase

    @RelaxedMockK
    private lateinit var userFollowUseCase: UserFollowUseCase

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        every { newsFeedUseCase.getNewsFeed() } returns flowOf(Outcome.empty())
        every { userSearchUseCase.getUserSearchResults() } returns flowOf(Outcome.empty())
        sut = spyk(
            UserActivityViewModel(
                newsFeedUseCase,
                userSearchUseCase,
                userFollowUseCase,
            )
        )
    }

    @Test
    fun `onNewsFeedRefresh, calls updateNewsFeed from use case`() {
        //Assemble
        //Act
        sut.onNewsFeedRefresh()
        //Assert
        coVerify { newsFeedUseCase.updateNewsFeed() }
    }

    @Test
    fun `onFollowButtonClicked, calls follow user from use case`() {
        //Assemble
        val user = User("", "", "", false)
        //Act
        sut.onFollowButtonClicked(user)
        //Assert
        coVerify { userFollowUseCase.followUser(user) }
    }

    @Test
    fun `onFollowButtonClicked, when failure, emits correct states`() = runBlockingTest {
        //Assemble
        val user = User("", "", "", false)
        coEvery { userFollowUseCase.followUser(any()) } returns Outcome.failure()
        //Act
        sut.userFollowEvent.test {
            sut.onFollowButtonClicked(user)
            //Assert
            val loading = expectItem()
            val failure = expectItem()
            assertEquals(UserFollowEvent.LOADING, loading)
            assertEquals(UserFollowEvent.FAILURE, failure)
        }
    }

    @Test
    fun `onFollowButtonClicked, when success, emits correct states`() = runBlockingTest {
        //Assemble
        val user = User("", "", "", false)
        coEvery { userFollowUseCase.followUser(any()) } returns Outcome.success(listOf())
        //Act
        sut.userFollowEvent.test {
            sut.onFollowButtonClicked(user)
            //Assert
            val loading = expectItem()
            val success = expectItem()
            assertEquals(UserFollowEvent.LOADING, loading)
            assertEquals(UserFollowEvent.SUCCESS, success)
        }
    }

    @Test
    fun `onFollowButtonClicked, when empty, emits correct states`() = runBlockingTest {
        //Assemble
        val user = User("", "", "", false)
        coEvery { userFollowUseCase.followUser(any()) } returns Outcome.empty()
        //Act
        sut.userFollowEvent.test {
            sut.onFollowButtonClicked(user)
            //Assert
            val loading = expectItem()
            val empty = expectItem()
            assertEquals(UserFollowEvent.LOADING, loading)
            assertEquals(UserFollowEvent.SUCCESS, empty)
        }
    }

    @Test
    fun `onUnfollowButtonClicked, calls unfollow user from use case`() {
        //Assemble
        val user = User("", "", "", false)
        //Act
        sut.onUnfollowButtonClicked(user)
        //Assert
        coVerify { userFollowUseCase.unfollowUser(user) }
    }

    @Test
    fun `onUnfollowButtonClicked, when failure, emits correct states`() = runBlockingTest {
        //Assemble
        val user = User("", "", "", false)
        coEvery { userFollowUseCase.unfollowUser(any()) } returns Outcome.failure()
        //Act
        sut.userUnfollowEvent.test {
            sut.onUnfollowButtonClicked(user)
            //Assert
            val loading = expectItem()
            val failure = expectItem()
            assertEquals(UserUnfollowEvent.LOADING, loading)
            assertEquals(UserUnfollowEvent.FAILURE, failure)
        }
    }

    @Test
    fun `onUnfollowButtonClicked, when success, emits correct states`() = runBlockingTest {
        //Assemble
        val user = User("", "", "", false)
        coEvery { userFollowUseCase.unfollowUser(any()) } returns Outcome.success(listOf())
        //Act
        sut.userUnfollowEvent.test {
            sut.onUnfollowButtonClicked(user)
            //Assert
            val loading = expectItem()
            val success = expectItem()
            assertEquals(UserUnfollowEvent.LOADING, loading)
            assertEquals(UserUnfollowEvent.SUCCESS, success)
        }
    }

    @Test
    fun `onUnfollowButtonClicked, when empty, emits correct states`() = runBlockingTest {
        //Assemble
        val user = User("", "", "", false)
        coEvery { userFollowUseCase.unfollowUser(any()) } returns Outcome.empty()
        //Act
        sut.userUnfollowEvent.test {
            sut.onUnfollowButtonClicked(user)
            //Assert
            val loading = expectItem()
            val empty = expectItem()
            assertEquals(UserUnfollowEvent.LOADING, loading)
            assertEquals(UserUnfollowEvent.SUCCESS, empty)
        }
    }

    @Test
    fun `onSearchCleared, calls clearUserSearchResults from use case`() {
        //Assemble
        //Act
        sut.onSearchCleared()
        //Assert
        verify { userSearchUseCase.clearUserSearchResults() }
    }

    @Test
    fun `onSearchCleared, disables searchState`() = runBlockingTest {
        //Assemble
        //Act
        sut.userSearchState.test {
            sut.onSearchCleared()
            val disabled = expectItem()
            //Assert
            assertTrue(disabled is UserSearchState.Disabled)
        }
    }

    @Test
    fun `onSearchCleared, enables newsFeedState`() = runBlockingTest {
        //Assemble
        //Act
        sut.newsFeedState.test {
            sut.onSearchCleared()
            val enabled = expectItem()
            //Assert
            assertTrue(enabled is NewsFeedState.NewsFeed)
        }
    }

    @Test
    fun `searchUsers, if name is blank, calls onSearchCleared`() {
        //Assemble
        //Act
        sut.searchUsers("")
        //Assert
        verify { sut.onSearchCleared() }
    }

    @Test
    fun `searchUsers, if name is blank, disables user search state`() = runBlockingTest {
        //Assemble
        //Act
        sut.userSearchState.test {
            sut.searchUsers("")
            cancelAndIgnoreRemainingEvents()
        }
        //Assert
        assertEquals(UserSearchState.Disabled, sut.userSearchState.value)
    }

    @Test
    fun `searchUsers, if name is not blank, enables user search state`() = runBlockingTest {
        //Assemble
        //Act
        sut.userSearchState.test {
            sut.searchUsers("not blank")
            cancelAndIgnoreRemainingEvents()
        }
        //Assert
        assertTrue(sut.userSearchState.value is UserSearchState.UserSearch)
    }

    @Test
    fun `searchUsers, if name is blank, does not call searchUsers use case`() {
        //Assemble
        //Act
        sut.searchUsers("")
        //Assert
        coVerify(exactly = 0) { userSearchUseCase.searchUsers(any()) }
    }

    @Test
    fun `searchUsers, if name is not blank, calls searchUsers use case`() {
        //Assemble
        val name = "test"
        //Act
        sut.searchUsers(name)
        //Assert
        coVerify { userSearchUseCase.searchUsers(name) }
    }

    @Test
    fun `init, updates news feed`() {
        //Assert
        coVerify { newsFeedUseCase.updateNewsFeed() }
    }
}