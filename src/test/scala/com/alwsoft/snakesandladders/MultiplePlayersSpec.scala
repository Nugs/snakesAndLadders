package com.alwsoft.snakesandladders

import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, FeatureSpec, GivenWhenThen, Matchers}

class MultiplePlayersSpec extends FeatureSpec with GivenWhenThen with Matchers with MockitoSugar with BeforeAndAfterEach {

  private val mockDice = mock[Dice]
  private var game: SnakesAndLadders = SnakesAndLadders(mockDice)

  override def beforeEach: Unit = {
    game = SnakesAndLadders(mockDice)
    reset(mockDice)
  }

  feature("Determining play order") {
    info("As a player")
    info("I want to influence the play order")
    info("So that player 1 doesn't always go first")

    scenario("1") {
      Given("there are two players")
      When("the game is started")
      Then("the players must roll dice to determine their play order")
      pending
    }

    scenario("2") {
      Given("the players are rolling to determine play order")
      When("Player 1 rolls higher than Player 2")
      Then("Player 1 rolls first")
      pending
    }

    scenario("3") {
      Given("the players are rolling to determine play order")
      When("Player 2 rolls higher than Player 1")
      Then("Player 2 rolls first")
      pending
    }

    scenario("4") {
      Given("the players are rolling to determine play order")
      When("Player 1 rolls the same as Player 2")
      Then("the players must roll again")
      pending
    }
  }

  feature("Following play order") {
    info("As a player")
    info("I want to follow the play order")
    info("So that the game is more fair")

    scenario("1") {
      Given("it is Player 1's turn")
      val p1 = game.addPlayer("player1")
      val p2 = game.addPlayer("player2")
      game.nextToPlay shouldBe Some(p1)

      When("they have moved their token")
      game.move(p1)

      Then("it is Player 2's turn")
      game.nextToPlay shouldBe Some(p2)
    }

    scenario("2") {
      Given("it is Player 2's turn")
      val p1 = game.addPlayer("player1")
      val p2 = game.addPlayer("player2")
      game.move(p1)
      game.nextToPlay shouldBe Some(p2)

      When("they have moved their token")
      game.move(p2)

      Then("it is Player 1's turn")
      game.nextToPlay shouldBe Some(p1)
    }
  }
}
