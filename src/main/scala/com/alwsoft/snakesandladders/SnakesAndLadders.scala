package com.alwsoft.snakesandladders

import scala.util.Random

case class Token(playerName: String)
case class Dice(maxValue: Int) {
  def roll():Int = Random.nextInt(maxValue) + 1
}

case class SnakesAndLadders() {

  var tokenLocation: Map[Token, Int] = Map()

  def location(token: Token): Int = tokenLocation.getOrElse(token, -1)

  def move(token: Token, distance: Int): Unit = tokenLocation += (token -> (distance + location(token)))

  def addPlayer(name: String): Token = {
    val newPlayer = Token(name)
    tokenLocation += (newPlayer -> 1)
    newPlayer
  }
}
