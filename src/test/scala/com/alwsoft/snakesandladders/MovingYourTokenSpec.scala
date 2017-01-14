package com.alwsoft.snakesandladders

import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}

class MovingYourTokenSpec extends FeatureSpec with GivenWhenThen with Matchers {
  feature("Moving your token") {
    info("As a player")
    info("I want to be able to move my token")
    info("So that I can get closer to the goal")

    scenario("1") {
      Given("the game is started")
      val game = SnakesAndLadders()

      When("the token is placed on the board")
      val token1 = game.addPlayer("Player1")

      Then("the token is on square 1")
      game.location(token1) shouldBe 1
    }

    scenario("2") {
      Given("the token is on square 1")
      val game = SnakesAndLadders()
      val token1 = game.addPlayer("Player1")

      When("the token is moved 3 spaces")
      game.move(token1, 3)

      Then("the token is on square 4")
      game.location(token1) shouldBe 4
    }

    scenario("3") {
      Given("the token is on square 1")
      val game = SnakesAndLadders()
      val token1 = game.addPlayer("Player1")

      When("the token is moved 3 spaces")
      game.move(token1, 3)

      And("it is moved 4 spaces")
      game.move(token1, 4)

      Then("the token is on square 8")
      game.location(token1) shouldBe 8
    }
  }

  feature("Player Can Win the Game") {
    info("As a player")
    info("I want to move my token based on the roll of a die")
    info("So that there is an element of chance in the game")

    scenario("1") {
      Given("the game is started")
      val game = SnakesAndLadders()
      val token1 = game.addPlayer("Player1")

      When("the player rolls a die")
      val dice: Dice = Dice(6)

      Then("the result should be between 1-6 inclusive")
      val validRolls = 1 to 6
      (1 to 100).forall(r => validRolls.contains(dice.roll())) shouldBe true
    }
    
    scenario("2") {
      Given("the player rolls a 4")
      When("they move their token")
      Then("the token should move 4 spaces")
      pending
    }
  }
}
