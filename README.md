Snakes & Ladders [![Build Status](https://travis-ci.org/Nugs/snakesAndLadders.svg?branch=master)](https://travis-ci.org/Nugs/snakesAndLadders)
================================================================================================

Build
-----

The application is built using SBT (0.13) and Scala (2.12)
The only library dependencies are ScalaTest (3.0.1) and Mockito (2.6.2)

Run
---
The applicaion has no 'runnable' frontend - testing of the public facing API has been done
using scalaTest. The test suite can be run via sbt: `sbt test` or via IDE integrations.
A travis build has been configured (linked above) to illustrate the result of running the test
suite.

Testing
-------

The application was built using TDD principles with regular commits - `git log`
should demonstrate the process taken.

Testing was done using ScalaTest with Mockito where appropriate (generally for mocking
of 'random' dice rolls) and used the 'feature' style to allow a natural translation
of the features described within the Kata to form the underlying basis of the test suite.

Features 1 - 3 have been delivered with feature 4 not being achieved within the timeframe.

Assumptions
-----------

* No UI has been created as this was beyond the scope of the exercise
* The feature specs as defined within the kata have been followed - further enhancements
were considered but, the kata features were assumed to be the priorities of the exercise
(further feature ideas would be added to the backlog for refinement and planning)
* A dice roll is asumed to be internal to the system, not a provided value by the user
* Basic documentation is in place for public methods - those that could conceivably form part
of the public API

Enhancements
------------

For addition to the product backlog:

* Define a mechanism for building a board (or rules for random creation - # snakes/ladders)
* Return a value from a 'move' call to allow the frontend to 'visualise' the new location of
a token without having to make a successive call to 'location'
the re-rolls then sit relative to them within the play order?
* Build RESTful api for the application
* Persistence of game state

Things that could be better
---------------------------
* 'rollForStartOrder' could probably do with some enhancement and possibly benefit from more
clarity on what 'reroll on same value' means in terms of players who didn't 'tie' - where do
they sit in the overall turn list?
* Boundary conditions - a snake or ladder could be defined beyond the bounds of the board for example
* Don't like the use of vars - would like to have constructed the Game with a full set of snakes/ladders
from the oustset and have fixtures as a val
