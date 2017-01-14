package com.alwsoft.snakesandladders

import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, FeatureSpec, GivenWhenThen, Matchers}

class SnakesAndLaddersSpec extends FeatureSpec with GivenWhenThen with Matchers with MockitoSugar with BeforeAndAfterEach {

  private val mockDice = mock[Dice]
  private var game: SnakesAndLadders = SnakesAndLadders(mockDice)

  override def beforeEach: Unit = {
    game = SnakesAndLadders(mockDice)
    reset(mockDice)
  }

  feature("Snakes go down, not up") {
    info("As a player")
    info("I want fixtures to move my token down")
    info("So that the game is more fun")

    scenario("1") {
      Given("there is a snake connecting squares 2 and 12")
      game.addSnake(12 -> 2)

      When("the token lands on square 12")
      val token1 = game.addPlayer("Player1")
      when(mockDice.roll()).thenReturn(11)
      game.move(token1)

      Then("the token is on square 2")
      game.location(token1) shouldBe 2
    }

    scenario("2") {
      Given("there is a snake connecting squares 2 and 12")
      game.addSnake(12 -> 2)

      When("the token lands on square 2")
      val token1 = game.addPlayer("Player1")
      when(mockDice.roll()).thenReturn(1)
      game.move(token1)

      Then("the token is on square 2  ")
      game.location(token1) shouldBe 2
    }
  }

  feature("Ladders Go Up, Not Down") {
    info("As a player")
    info("I want ladders to move my token up")
    info("So that the game is more fun")

    scenario("1") {
      Given("there is a ladder connecting squares 2 and 12")
      game.addLadder(2 -> 12)

      When("the token lands on square 2")
      val token1 = game.addPlayer("Player1")
      when(mockDice.roll()).thenReturn(1)
      game.move(token1)

      Then("the token is on square 12")
      game.location(token1) shouldBe 12
    }

    scenario("2") {
      Given("there is a ladder connecting squares 2 and 12")
      game.addLadder(2 -> 12)

      When("the token lands on square 12")
      val token1 = game.addPlayer("Player1")
      when(mockDice.roll()).thenReturn(11)
      game.move(token1)

      Then("the token is on square 12")
      game.location(token1) shouldBe 12
    }

    scenario("3") {
      When("trying to create a snake in the wrong direction")
      Then("a runtime exception is thrown")
      a[BoardFixtureException] shouldBe thrownBy {
        game.addSnake(2 -> 12)
      }
    }

    scenario("4") {
      When("trying to create a ladder in the wrong direction")
      Then("a runtime exception is thrown")
      a[BoardFixtureException] shouldBe thrownBy {
        game.addLadder(12 -> 2)
      }
    }

    scenario("5") {
      Given("A game has started")
      game.addPlayer("Player 1")

      When("trying to add 'fixtures' to the board")
      Then("a runtime exception is thrown")
      a[GameStateException] shouldBe thrownBy {
        game.addLadder(2 -> 12)
      }
    }

    scenario("6") {
      Given("a ladder already exists from 2 -> 12")
      game.addLadder(2 -> 12)

      When("attempting to add another ladder from 2 -> 22")
      Then("an exception is thrown")
      a[BoardFixtureException] shouldBe thrownBy {
        game.addLadder(2 -> 22)
      }
    }

    scenario("7") {
      Given("a ladder already exists from 2 -> 12")
      game.addLadder(2 -> 12)

      When("attempting to add a snake from 12 -> 2")
      Then("an exception is thrown")
      a[BoardFixtureException] shouldBe thrownBy {
        game.addSnake(12 -> 2)
      }
    }
  }
}
