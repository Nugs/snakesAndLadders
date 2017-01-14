package com.alwsoft.snakesandladders

import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, FeatureSpec, GivenWhenThen, Matchers}

class MovingYourTokenSpec extends FeatureSpec with GivenWhenThen with Matchers with MockitoSugar with BeforeAndAfterEach {

  private val mockDice = mock[Dice]
  private var game: SnakesAndLadders = SnakesAndLadders(mockDice)

  override def beforeEach: Unit = {
    game = SnakesAndLadders(mockDice)
    reset(mockDice)
  }

  feature("Moving your token") {
    info("As a player")
    info("I want to be able to move my token")
    info("So that I can get closer to the goal")

    scenario("1") {
      Given("the game is started")
      When("the token is placed on the board")
      val token1 = game.addPlayer("Player1")

      Then("the token is on square 1")
      game.location(token1) shouldBe 1
    }

    scenario("2") {
      Given("the token is on square 1")
      val token1 = game.addPlayer("Player1")
      game.location(token1) shouldBe 1

      When("the token is moved 3 spaces")
      when(mockDice.roll()).thenReturn(3)
      game.move(token1)

      Then("the token is on square 4")
      game.location(token1) shouldBe 4
    }

    scenario("3") {
      Given("the token is on square 1")
      val token1 = game.addPlayer("Player1")
      game.location(token1) shouldBe 1

      When("the token is moved 3 spaces")
      when(mockDice.roll()).thenReturn(3)
      game.move(token1)

      And("it is moved 4 spaces")
      when(mockDice.roll()).thenReturn(4)
      game.move(token1)

      Then("the token is on square 8")
      game.location(token1) shouldBe 8
    }
  }

  feature("Moves are determined by dice rolls") {
    info("As a player")
    info("I want to move my token based on the roll of a die")
    info("So that there is an element of chance in the game")

    scenario("1") {
      Given("the game is started")
      val token1 = game.addPlayer("Player1")

      When("the player rolls a die")
      val dice: Dice = Dice(6)

      Then("the result should be between 1-6 inclusive")
      val validRolls = 1 to 6
      (1 to 100).forall(r => validRolls.contains(dice.roll())) shouldBe true
    }
    
    scenario("2") {
      val token1 = game.addPlayer("Player1")

      Given("the player rolls a 4")
      when(mockDice.roll()) thenReturn 4

      When("they move their token")
      game.move(token1)

      Then("the token should move 4 spaces")
      game.location(token1) shouldBe 5
    }
  }

  feature("Player can win the game") {
    info("As a player")
    info("I want to move my token based on the roll of a die")
    info("So that there is an element of chance in the game")

    scenario("1") {
      Given("the token is on square 97")
      val token1 = game.addPlayer("Player1")
      when(mockDice.roll()).thenReturn(96)
      game.move(token1)
      game.location(token1) shouldBe 97

      When("the token is moved 3 spaces")
      when(mockDice.roll()).thenReturn(3)
      game.move(token1)

      Then("the token is on square 100")
      game.location(token1) shouldBe 100

      And("the player has won the game")
      game.winner() shouldBe Some(token1)
    }

    scenario("2") {
      Given("the token is on square 97")
      When("the token is moved 4 spaces")
      Then("the token is on square 97")
      And("the player has not won the game")
      pending
    }
  }
}
