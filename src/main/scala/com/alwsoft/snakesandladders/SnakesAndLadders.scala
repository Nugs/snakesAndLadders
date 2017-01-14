package com.alwsoft.snakesandladders

import scala.util.Random

case class BoardFixtureException(message: String) extends RuntimeException(message)
case class GameStateException(message: String) extends RuntimeException(message)

case class Token(playerName: String)
case class Dice(maxValue: Int) {
  def roll():Int = Random.nextInt(maxValue) + 1
}

case class SnakesAndLadders(dice: Dice = Dice(6), boardSize: Int = 100) {
  var tokenLocation: Map[Token, Int] = Map()
  var fixtures: Map[Int, Int] = Map()

  def gameIsStarted: Boolean = tokenLocation.nonEmpty

  def addLadder(ladder: (Int, Int)):Unit = {
    if(ladder._1 > ladder._2) throw BoardFixtureException("Ladders must go down")
    addFixture(ladder)
  }

  def addSnake(snake: (Int, Int)): Unit = {
    if(snake._1 < snake._2) throw BoardFixtureException("Snakes must go down")
    addFixture(snake)
  }

  private def addFixture(fixture: (Int, Int)): Unit = {
    if(gameIsStarted)
      throw GameStateException("Cannot modify board once game has started")
    else if(fixtures.exists(e => e._1 == fixture._1 || e._2 == fixture._1))
      throw BoardFixtureException("Fixture cannot start/end on the same square as another")
    else
      fixtures += fixture
  }

  def playerOnWinningSquare: ((Token, Int)) => Boolean = entry => entry._2 == boardSize

  def winner(): Option[Token] = tokenLocation.find(playerOnWinningSquare).map(_._1)

  def location(token: Token): Int = tokenLocation.getOrElse(token, -1)

  def move(token: Token): Unit = {
    tokenLocation += {
      dice.roll() + location(token) match {
        // If the roll would take you beyond the end of the board, don't do anything
        case newLocation if newLocation > boardSize => token -> location(token)
        // If there is a fixture at the location you arrive on, go to the other end of the fixture (i.e. a snake or a ladder)
        case newLocation if fixtures.isDefinedAt(newLocation) => token -> fixtures(newLocation)
        case newLocation => token -> newLocation
      }
    }
  }

  def addPlayer(name: String): Token = {
    val newPlayer = Token(name)
    tokenLocation += (newPlayer -> 1)
    newPlayer
  }
}
