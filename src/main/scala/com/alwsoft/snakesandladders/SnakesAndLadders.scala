package com.alwsoft.snakesandladders

case class SnakesAndLadders() {
  case class Token(playerName: String)

  def location(token: Token) = 1

  def move(token: Token, distance: Int) = ???

  def addPlayer(name: String) = Token(name)
}
