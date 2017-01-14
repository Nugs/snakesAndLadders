package com.alwsoft.snakesandladders

import scala.util.Random

case class Token(playerName: String)
case class Dice(maxValue: Int) {
  def roll():Int = Random.nextInt(maxValue) + 1
}

case class SnakesAndLadders(dice: Dice = Dice(6), boardSize: Int = 100) {
  var tokenLocation: Map[Token, Int] = Map()
  var fixtures: Map[Int, Int] = Map()

  def addLadder(ladder: (Int, Int)):Unit = fixtures += ladder
  def addSnake(snake: (Int, Int)): Unit = fixtures += snake

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
