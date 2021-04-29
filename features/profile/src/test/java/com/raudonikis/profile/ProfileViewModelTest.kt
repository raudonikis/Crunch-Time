package com.raudonikis.profile

import app.cash.turbine.test
import com.raudonikis.common.Outcome
import com.raudonikis.common_testing.MainCoroutineRule
import com.raudonikis.common_ui.follow.UserFollowEvent
import com.raudonikis.common_ui.follow.UserUnfollowEvent
import com.raudonikis.data_domain.activity.usecases.MyActivityUseCase
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game.models.GameCollectionType
import com.raudonikis.data_domain.game_collection.GameCollectionUseCase
import com.raudonikis.data_domain.user.User
import com.raudonikis.data_domain.user.UserPreferences
import com.raudonikis.data_domain.user_follow.UserFollowUseCase
import com.raudonikis.data_domain.user_following.UserFollowingUseCase
import com.raudonikis.navigation.NavigationDispatcher
import com.raudonikis.profile.followers.FollowerType
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class ProfileViewModelTest {

    private lateinit var sut: ProfileViewModel

    @RelaxedMockK
    private lateinit var userPreferences: UserPreferences

    @RelaxedMockK
    private lateinit var gameCollectionUseCase: GameCollectionUseCase

    @RelaxedMockK
    private lateinit var userFollowingUseCase: UserFollowingUseCase

    @RelaxedMockK
    private lateinit var myActivityUseCase: MyActivityUseCase

    @RelaxedMockK
    private lateinit var userFollowUseCase: UserFollowUseCase

    @RelaxedMockK
    private lateinit var navigationDispatcher: NavigationDispatcher

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        initialiseSut()
    }

    private fun initialiseSut() {
        sut = ProfileViewModel(
            navigationDispatcher,
            userPreferences,
            gameCollectionUseCase,
            userFollowingUseCase,
            myActivityUseCase,
            userFollowUseCase,
        )
    }

    @Test
    fun `onGameClicked, navigates to details`() {
        //Assemble
        val game = Game()
        //Act
        sut.onGameClicked(game)
        //Assert
        verify { navigationDispatcher.navigate(ProfileRouter.profileToDetails(game)) }
    }

    @Test
    fun `onFollowersClicked, navigates to followers`() {
        //Assemble
        //Act
        sut.onFollowersClicked()
        //Assert
        verify { navigationDispatcher.navigate(ProfileRouter.profileToFollowers(FollowerType.FOLLOWER)) }
    }

    @Test
    fun `onFollowingClicked, navigates to following`() {
        //Assemble
        //Act
        sut.onFollowingClicked()
        //Assert
        verify { navigationDispatcher.navigate(ProfileRouter.profileToFollowers(FollowerType.FOLLOWING)) }
    }


    @Test
    fun `onFollowButtonClicked, calls followUser from use case`() {
        //Assemble
        val user = User()
        //Act
        sut.onFollowButtonClicked(user)
        //Assert
        coVerify { userFollowUseCase.followUser(user) }
    }

    @Test
    fun `onUnfollowButtonClicked, calls unfollowUser from use case`() {
        //Assemble
        val user = User()
        //Act
        sut.onUnfollowButtonClicked(user)
        //Assert
        coVerify { userFollowUseCase.unfollowUser(user) }
    }

    @Test
    fun `userFollowEvent, emits correct states when success`() = runBlockingTest {
        //Assemble
        coEvery { userFollowUseCase.followUser(any()) } returns Outcome.success(listOf())
        val user = User()
        //Act
        sut.userFollowEvent.test {
            sut.onFollowButtonClicked(user)
            val loading = expectItem()
            val success = expectItem()
            //Assert
            assertEquals(UserFollowEvent.LOADING, loading)
            assertEquals(UserFollowEvent.SUCCESS, success)
        }
    }

    @Test
    fun `userFollowEvent, emits correct states when failure`() = runBlockingTest {
        //Assemble
        coEvery { userFollowUseCase.followUser(any()) } returns Outcome.failure()
        val user = User()
        //Act
        sut.userFollowEvent.test {
            sut.onFollowButtonClicked(user)
            val loading = expectItem()
            val failure = expectItem()
            //Assert
            assertEquals(UserFollowEvent.LOADING, loading)
            assertEquals(UserFollowEvent.FAILURE, failure)
        }
    }

    @Test
    fun `userFollowEvent, emits correct states when empty`() = runBlockingTest {
        //Assemble
        coEvery { userFollowUseCase.followUser(any()) } returns Outcome.empty()
        val user = User()
        //Act
        sut.userFollowEvent.test {
            sut.onFollowButtonClicked(user)
            val loading = expectItem()
            val success = expectItem()
            //Assert
            assertEquals(UserFollowEvent.LOADING, loading)
            assertEquals(UserFollowEvent.SUCCESS, success)
        }
    }

    @Test
    fun `userUnfollowEvent, emits correct states when success`() = runBlockingTest {
        //Assemble
        coEvery { userFollowUseCase.unfollowUser(any()) } returns Outcome.success(listOf())
        val user = User()
        //Act
        sut.userUnfollowEvent.test {
            sut.onUnfollowButtonClicked(user)
            val loading = expectItem()
            val success = expectItem()
            //Assert
            assertEquals(UserUnfollowEvent.LOADING, loading)
            assertEquals(UserUnfollowEvent.SUCCESS, success)
        }
    }

    @Test
    fun `userUnfollowEvent, emits correct states when failure`() = runBlockingTest {
        //Assemble
        coEvery { userFollowUseCase.unfollowUser(any()) } returns Outcome.failure()
        val user = User()
        //Act
        sut.userUnfollowEvent.test {
            sut.onUnfollowButtonClicked(user)
            val loading = expectItem()
            val failure = expectItem()
            //Assert
            assertEquals(UserUnfollowEvent.LOADING, loading)
            assertEquals(UserUnfollowEvent.FAILURE, failure)
        }
    }

    @Test
    fun `userUnfollowEvent, emits correct states when empty`() = runBlockingTest {
        //Assemble
        coEvery { userFollowUseCase.unfollowUser(any()) } returns Outcome.empty()
        val user = User()
        //Act
        sut.userUnfollowEvent.test {
            sut.onUnfollowButtonClicked(user)
            val loading = expectItem()
            val success = expectItem()
            //Assert
            assertEquals(UserUnfollowEvent.LOADING, loading)
            assertEquals(UserUnfollowEvent.SUCCESS, success)
        }
    }

    @Test
    fun `init, update game collections`() {
        //Assert
        coVerify { gameCollectionUseCase.updateGameCollection() }
    }

    @Test
    fun `init, update my activity`() {
        //Assert
        coVerify { myActivityUseCase.updateMyUserActivity() }
    }

    @Test
    fun `init, update following users`() {
        //Assert
        coVerify { userFollowingUseCase.updateFollowingUsers() }
    }

    @Test
    fun `init, update current user`() {
        //Assemble
        val user = User(name = "test")
        every { userPreferences.currentUser } returns user
        //Act
        initialiseSut()
        //Assert
        assertEquals(Outcome.success(user), sut.userState.value)
    }

    @Test
    fun `onGameCollectionTabSwitched, update game collection type`() {
        //Assemble
        val type = GameCollectionType.WANT
        //Act
        sut.onGameCollectionTabSwitched(type)
        //Assert
        assertEquals(type, sut.gameCollectionTypeState.value)
    }

    @Test
    fun `onGameCollectionTabSwitched, if null, do not update game collection type`() {
        //Assemble
        val type = null
        //Act
        val lastGameCollectionType = sut.gameCollectionTypeState.value
        sut.onGameCollectionTabSwitched(type)
        //Assert
        assertEquals(lastGameCollectionType, sut.gameCollectionTypeState.value)
    }
}