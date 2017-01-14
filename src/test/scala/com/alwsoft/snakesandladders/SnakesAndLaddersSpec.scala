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
    info("I want snakes to move my token down")
    info("So that the game is more fun")

    scenario("1") {
      Given("there is a snake connecting squares 2 and 12")
      When("the token lands on square 12")
      Then("the token is on square 2")
      pending
    }

    scenario("2") {
      Given("there is a snake connecting squares 2 and 12")
      When("the token lands on square 2")
      Then("the token is on square 2  ")
      pending
    }
  }
}
