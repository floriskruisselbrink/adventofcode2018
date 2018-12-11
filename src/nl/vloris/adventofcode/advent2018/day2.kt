package nl.vloris.adventofcode.advent2018

object Day2 {
    private val input = javaClass.getResource("day2-input.txt")
        .readText()
        .lines()
        .filter { it.isNotEmpty() }

    private fun xOfAnyLetter(boxid: String, letterCount: Int): Boolean {
        val letterMap = mutableMapOf<Char, Int>()
        boxid.toCharArray().forEach {
            val count = letterMap.getOrDefault(it, 0)
            letterMap[it] = count + 1
        }

        return letterMap.filter { it.value == letterCount }.isNotEmpty()
    }

    private fun twoOfAnyLetter(boxid: String): Boolean {
        return xOfAnyLetter(boxid, 2)
    }

    private fun threeOfAnyLetter(boxid: String): Boolean {
        return xOfAnyLetter(boxid, 3)
    }

    fun answerPart1(): Int {
        val two = input.filter { twoOfAnyLetter(it) }.count()
        val three = input.filter { threeOfAnyLetter(it) }.count()

        return two * three
    }
}

fun main(args: Array<String>) {
    println("Day2 part1: " + Day2.answerPart1())
}