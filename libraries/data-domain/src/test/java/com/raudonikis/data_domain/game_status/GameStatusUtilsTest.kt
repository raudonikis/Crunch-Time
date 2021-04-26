package com.raudonikis.data_domain.game_status

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.game.models.Game
import junit.framework.Assert.assertEquals
import org.junit.Test

class GameStatusUtilsTest {

    @Test
    fun `updateGameStatusForGames, one game matches, updated`() {
        //Assemble
        val games = listOf(
            Game(id = 1, status = GameStatus.EMPTY),
            Game(id = 2, status = GameStatus.EMPTY),
            Game(id = 3, status = GameStatus.EMPTY),
            Game(id = 4, status = GameStatus.EMPTY),
            Game(id = 5, status = GameStatus.EMPTY),
            Game(id = 6, status = GameStatus.EMPTY),
        )
        val outcome = Outcome.success(games)
        val newStatus = GameStatus.WANT
        val gameId: Long = 1
        val expectedResult = Outcome.success(
            listOf(
                Game(id = 1, status = GameStatus.WANT),
                Game(id = 2, status = GameStatus.EMPTY),
                Game(id = 3, status = GameStatus.EMPTY),
                Game(id = 4, status = GameStatus.EMPTY),
                Game(id = 5, status = GameStatus.EMPTY),
                Game(id = 6, status = GameStatus.EMPTY),
            )
        )
        //Act
        val result = GameStatusUtils.updateGameStatusForGames(outcome, gameId, newStatus)
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `updateGameStatusForGames, two games match, updated`() {
        //Assemble
        val games = listOf(
            Game(id = 1, status = GameStatus.EMPTY),
            Game(id = 2, status = GameStatus.EMPTY),
            Game(id = 1, status = GameStatus.EMPTY),
            Game(id = 4, status = GameStatus.EMPTY),
            Game(id = 5, status = GameStatus.EMPTY),
            Game(id = 6, status = GameStatus.EMPTY),
        )
        val outcome = Outcome.success(games)
        val newStatus = GameStatus.WANT
        val gameId: Long = 1
        val expectedResult = Outcome.success(
            listOf(
                Game(id = 1, status = GameStatus.WANT),
                Game(id = 2, status = GameStatus.EMPTY),
                Game(id = 1, status = GameStatus.WANT),
                Game(id = 4, status = GameStatus.EMPTY),
                Game(id = 5, status = GameStatus.EMPTY),
                Game(id = 6, status = GameStatus.EMPTY),
            )
        )
        //Act
        val result = GameStatusUtils.updateGameStatusForGames(outcome, gameId, newStatus)
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `updateGameStatusForGames, all games match, updated`() {
        //Assemble
        val games = listOf(
            Game(id = 1, status = GameStatus.EMPTY),
            Game(id = 1, status = GameStatus.EMPTY),
            Game(id = 1, status = GameStatus.EMPTY),
            Game(id = 1, status = GameStatus.EMPTY),
            Game(id = 1, status = GameStatus.EMPTY),
            Game(id = 1, status = GameStatus.EMPTY),
        )
        val outcome = Outcome.success(games)
        val newStatus = GameStatus.WANT
        val gameId: Long = 1
        val expectedResult = Outcome.success(
            listOf(
                Game(id = 1, status = GameStatus.WANT),
                Game(id = 1, status = GameStatus.WANT),
                Game(id = 1, status = GameStatus.WANT),
                Game(id = 1, status = GameStatus.WANT),
                Game(id = 1, status = GameStatus.WANT),
                Game(id = 1, status = GameStatus.WANT),
            )
        )
        //Act
        val result = GameStatusUtils.updateGameStatusForGames(outcome, gameId, newStatus)
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `updateGameStatusForGames, no games match, not updated`() {
        //Assemble
        val games = listOf(
            Game(id = 1, status = GameStatus.EMPTY),
            Game(id = 2, status = GameStatus.EMPTY),
            Game(id = 3, status = GameStatus.EMPTY),
            Game(id = 4, status = GameStatus.EMPTY),
            Game(id = 5, status = GameStatus.EMPTY),
            Game(id = 6, status = GameStatus.EMPTY),
        )
        val outcome = Outcome.success(games)
        val newStatus = GameStatus.WANT
        val gameId: Long = 7
        val expectedResult = Outcome.success(games)
        //Act
        val result = GameStatusUtils.updateGameStatusForGames(outcome, gameId, newStatus)
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `updateGameStatusForGames, failure, not updated`() {
        //Assemble
        val outcome = Outcome.failure()
        val newStatus = GameStatus.WANT
        val gameId: Long = 7
        val expectedResult = Outcome.failure()
        //Act
        val result = GameStatusUtils.updateGameStatusForGames(outcome, gameId, newStatus)
        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `updateGameStatusForGames, empty, not updated`() {
        //Assemble
        val outcome = Outcome.empty()
        val newStatus = GameStatus.WANT
        val gameId: Long = 7
        val expectedResult = Outcome.empty()
        //Act
        val result = GameStatusUtils.updateGameStatusForGames(outcome, gameId, newStatus)
        //Assert
        assertEquals(expectedResult, result)
    }
}