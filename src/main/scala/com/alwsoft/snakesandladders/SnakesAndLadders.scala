package com.alwsoft.snakesandladders

case class SnakesAndLadders() {
  case class Token(playerName: String)

  var tokenLocation: Map[Token, Int] = Map()

  def location(token: Token): Int = tokenLocation.getOrElse(token, -1)

  def move(token: Token, distance: Int): Unit = tokenLocation += (token -> (distance + location(token)))

  def addPlayer(name: String): Token = {
    val newPlayer = Token(name)
    tokenLocation += (newPlayer -> 1)
    newPlayer
  }
}
