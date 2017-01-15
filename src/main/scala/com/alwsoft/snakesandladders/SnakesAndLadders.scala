package com.alwsoft.snakesandladders

import scala.util.Random

case class BoardFixtureException(message: String) extends RuntimeException(message)
case class GameStateException(message: String) extends RuntimeException(message)

case class Token(playerName: String, var moveCount: Int = 0, var startOrder: Option[Int] = None)

case class Dice(maxValue: Int) {
  def roll():Int = Random.nextInt(maxValue) + 1
}

case class SnakesAndLadders(dice: Dice = Dice(6), boardSize: Int = 100) {

  private var players: Seq[Token] = Seq()
  private var tokenLocation: Map[Token, Int] = Map()
  private var fixtures: Map[Int, Int] = Map()
  private def playerOnWinningSquare: ((Token, Int)) => Boolean = entry => entry._2 == boardSize
  private def startedOnly: Token => Boolean = t => t.startOrder.isDefined

  private def addFixture(fixture: (Int, Int)): Unit = {
    if(gameIsStarted)
      throw GameStateException("Cannot modify board once game has started")
    else if(fixtures.exists(e => e._1 == fixture._1 || e._2 == fixture._1))
      throw BoardFixtureException("Fixture cannot start/end on the same square as another")
    else
      fixtures += fixture
  }

  /**
    * Who is the next player to have a turn.
    * Optional - if no playrs have yet been added to the game and/or the initial roll for play order has not been done.
    * @return
    */
  def nextToPlay: Option[Token] = players.filter(startedOnly).sortBy(t => (t.moveCount, t.startOrder)).headOption

  /**
    * Is the game started - have tokens been added to the board.
    * @return Boolean
    */
  def gameIsStarted: Boolean = tokenLocation.nonEmpty

  /**
    * Add a ladder to the board.
    * Throws a BoardFixtureException if the ladder would be invalid.
    * @param ladder
    */
  def addLadder(ladder: (Int, Int)):Unit = {
    if(ladder._1 > ladder._2) throw BoardFixtureException("Ladders must go down")
    addFixture(ladder)
  }

  /**
    * Add a snake to the board.
    * Throws a BoardFixtureException if the snake would be invalid.
    * @param snake
    */
  def addSnake(snake: (Int, Int)): Unit = {
    if(snake._1 < snake._2) throw BoardFixtureException("Snakes must go down")
    addFixture(snake)
  }

  /**
    * Return an Option wrappng the winner (if the game is concluded) or None if not.
    * @return
    */
  def winner(): Option[Token] = tokenLocation.find(playerOnWinningSquare).map(_._1)

  /**
    * Return the location on the board of the given token.
    *
    * @param token
    * @return
    */
  def location(token: Token): Int = tokenLocation.getOrElse(token, -1)

  /**
    * Move the given token on the board (a dice roll will be made to determine the number of squares).
    * Throws GameStateException if the player passed is not the next to move.
    * @param token The player to move
    */
  def move(token: Token): Unit = {
    if(Option(token) != nextToPlay) throw GameStateException(s"Please wait for your turn (next up is $nextToPlay)")

    tokenLocation += {
      dice.roll() + location(token) match {
        // If the roll would take you beyond the end of the board, don't do anything
        case newLocation if newLocation > boardSize => token -> location(token)
        // If there is a fixture at the location you arrive on, go to the other end of the fixture (i.e. a snake or a ladder)
        case newLocation if fixtures.isDefinedAt(newLocation) => token -> fixtures(newLocation)
        case newLocation => token -> newLocation
      }
    }
    token.moveCount += 1
  }

  /**
    * Initiate the roll for play order. Each player will have their order of play established.
    * @param tokens
    * @return
    */
  def rollForStartOrder(tokens: Seq[Token] = players): Seq[Token] = {
    tokens.partition(t => t.startOrder.isEmpty) match {
      case (Seq(), done) => done
      case (toBeAllocated, done) =>
        toBeAllocated.foreach { p =>
          val startOrder = Some(dice.maxValue + 1 - dice.roll())
          toBeAllocated.filter(tba => tba.startOrder.isDefined).filter(q => q.startOrder == startOrder) match {
            case Seq() => p.startOrder = startOrder
            case likeRolls => likeRolls.foreach { r => r.startOrder = None }
          }
        }

        done ++ rollForStartOrder(toBeAllocated)
    }
  }

  /**
    * Add a player to the game
    * @param name Name of the player
    * @return
    */
  def addPlayer(name: String): Token = {
    val newPlayer = Token(name)
    tokenLocation += (newPlayer -> 1)
    players = players :+ newPlayer
    newPlayer
  }
}
