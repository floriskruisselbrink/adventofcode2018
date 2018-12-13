package nl.vloris.adventofcode.advent2018

import java.time.LocalDateTime

data class Player(val label: String) {
    var score = 0L
}

data class Marble(val value: Int) {
    var left: Marble = this
    var right: Marble = this

    override fun toString(): String {
        return value.toString()
    }
}

class Circle {
    private val firstMarble = Marble(0)
    private var current = firstMarble

    fun insertMarble(value: Int) {
        val newMarble = Marble(value)
        newMarble.left = current
        newMarble.right = current.right
        current.right.left = newMarble
        current.right = newMarble

        // lelijke hack om de eerste stap te fixen...
        if (current.left == current) { current.left = newMarble }

        current = newMarble
    }

    fun removeMarble(): Int {
        counterClockwise()

        val removed = current
        val right = removed.right
        val left = removed.left

        left.right = right
        right.left = left

        current = right

        return removed.value
    }

    fun clockwise() {
        current = current.right
    }

    fun counterClockwise() {
        current = current.left
    }

    override fun toString(): String {
        val builder = StringBuilder()

        var marble = firstMarble
        do {
            if (marble == current) {
                builder.append("($marble)")
            } else {
                builder.append(" $marble ")
            }
            marble = marble.right
        } while (marble != firstMarble)

        return builder.toString()
    }
}

class Game(private val players: List<Player>, private val lastMarble: Int) {
    private val circle = Circle()

    private fun nextTurn(marble: Int) {
        val player = players[(marble-1) % players.size]

        if (marble % 23 == 0) {
            repeat(7) { circle.counterClockwise() }

            player.score += marble
            player.score += circle.removeMarble()
        } else {
            circle.insertMarble(marble)
        }

        //printCircle(player)

        circle.clockwise()
    }

    private fun printCircle(player: Player) {
        println("[%3s] %s".format(player.label, circle.toString()))
    }

    fun play() {
        for (marble in 1..lastMarble) {
            nextTurn(marble)
        }
    }

    fun winningScore(): Long {
        return players.map { it.score }.max() ?: 0
    }
}

object Day9 {
    private fun calculateWinningScore(numPlayers: Int, lastMarble: Int): Long {
        val players = List(numPlayers) { i -> Player("${i + 1}") }
        val game = Game(players, lastMarble)
        game.play()

        return game.winningScore()
    }

    fun answerPart1(): Long {
        return calculateWinningScore(447, 71510)
    }

    fun answerPart2(): Long {
        return calculateWinningScore(447, 71510 * 100)
    }
}

fun main(args: Array<String>) {
    println(LocalDateTime.now())
    println("Day9, part1: " + Day9.answerPart1())
    println(LocalDateTime.now())
    println("Day9, part2: " + Day9.answerPart2())
    println(LocalDateTime.now())
}