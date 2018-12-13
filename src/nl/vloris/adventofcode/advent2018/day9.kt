package nl.vloris.adventofcode.advent2018

data class Player(val label: String) {
    var score = 0
}

class Game(private val players: List<Player>, private val lastMarble: Int) {

    private val circle = arrayListOf(0)
    private var currentPlayerIndex = 0
    private var currentCirclePosition = 1
    private var currentMarbleValue = 1

    private val currentPlayer get() = players[currentPlayerIndex]

    private fun calculateNextPosition(offset: Int): Int {
        val nextPosition = (currentCirclePosition + circle.size + offset) % circle.size
        return if (nextPosition == 0) {
            circle.size
        } else {
            nextPosition
        }
    }

    private fun insertMarble(): Int {
        circle.add(currentCirclePosition, currentMarbleValue)
        return calculateNextPosition(+2)
    }

    private fun removeMarble(): Int {
        // 1. add marble-value to score
        currentPlayer.score += currentMarbleValue

        // 2. add marble-value of 7 positions counter-clockwise to score
        currentCirclePosition = calculateNextPosition(-9)
        currentPlayer.score += circle[currentCirclePosition]
        circle.removeAt(currentCirclePosition)

        // 3. return position clockwise of removed marble
        return calculateNextPosition(+2)
    }

    private fun nextTurn() {
        val nextPosition = if (currentMarbleValue % 23 == 0) {
            removeMarble()
        } else {
            insertMarble()
        }

        //printCircle()

        currentCirclePosition = if (nextPosition == 0) {
            circle.size
        } else {
            nextPosition
        }
        currentMarbleValue++
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size
    }

    private fun printCircle() {
        val sb = StringBuilder()
        sb.append("[%3s] ".format(currentPlayer.label))

        circle.forEachIndexed { index, marble ->
            if (index == currentCirclePosition) {
                sb.append("($marble)")
            } else {
                sb.append(" $marble ")
            }
        }
        println(sb.toString())
    }

    fun play() {
        while (currentMarbleValue <= lastMarble) {
            nextTurn()
        }
    }

    fun winningScore(): Int {
        return players.map { it.score }.max() ?: 0
    }
}

object Day9 {
    private fun calculateWinningScore(numPlayers: Int, lastMarble: Int): Int {
        val players = List(numPlayers) { i -> Player("${i + 1}") }
        val game = Game(players, lastMarble)
        game.play()

        return game.winningScore()
    }

    fun answerPart1(): Int {
        //return calculateWinningScore(9, 25) // expected: 32
        //return calculateWinningScore(10, 1618) // expected: 8317
        //return calculateWinningScore(17, 1104) // expected: 2764
        return calculateWinningScore(447, 71510)
    }
}

fun main(args: Array<String>) {
    println("Day9, part1: " + Day9.answerPart1())
}