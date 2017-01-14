package com.alwsoft.snakesandladders

import scala.util.Random

case class Token(playerName: String)
case class Dice(maxValue: Int) {
  def roll():Int = Random.nextInt(maxValue) + 1
}

case class SnakesAndLadders(dice: Dice = Dice(6), boardSize: Int = 100) {
  var tokenLocation: Map[Token, Int] = Map()
  var snakes: Map[Int, Int] = Map()

  def addSnake(snake: (Int, Int)): Unit = snakes += snake

  def playerOnWinningSquare: ((Token, Int)) => Boolean = entry => entry._2 == boardSize

  def winner(): Option[Token] = tokenLocation.find(playerOnWinningSquare).map(_._1)

  def location(token: Token): Int = tokenLocation.getOrElse(token, -1)

  def move(token: Token): Unit = {
    tokenLocation += {
      dice.roll() + location(token) match {
        // If the roll would take you beyond the end of the board, don't do anything
        case newLocation if newLocation > boardSize => token -> location(token)
        // If there is a snake head at the location you arrive on, go to the tail end
        case newLocation if snakes.isDefinedAt(newLocation) => token -> snakes(newLocation)
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
