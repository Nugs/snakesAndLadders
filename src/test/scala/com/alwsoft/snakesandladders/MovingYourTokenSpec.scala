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
      val token = game.addPlayer("Player1")
      Then("the token is on square 1")
      token.location shouldBe 1
    }

    scenario("2") {
      Given("the token is on square 1")
      When("the token is moved 3 spaces")
      Then("the token is on square 4")
      pending
    }

    scenario("3") {
      Given("the token is on square 1")
      When("the token is moved 3 spaces")
      And("it is moved 4 spaces")
      Then("the token is on square 8")
      pending
    }
  }
}
