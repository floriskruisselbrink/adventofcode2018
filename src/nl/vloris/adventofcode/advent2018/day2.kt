package nl.vloris.adventofcode.advent2018

object Day2 {
    private val input = javaClass.getResource("day2-input.txt")
        .readText()
        .lines()
        .filter { it.isNotEmpty() }
        .map { it.toCharArray() }

    private fun xOfAnyLetter(boxid: CharArray, letterCount: Int): Boolean {
        val letterMap = mutableMapOf<Char, Int>()
        boxid.forEach {
            val count = letterMap.getOrDefault(it, 0)
            letterMap[it] = count + 1
        }

        return letterMap.filter { it.value == letterCount }.isNotEmpty()
    }

    private fun twoOfAnyLetter(boxid: CharArray): Boolean {
        return xOfAnyLetter(boxid, 2)
    }

    private fun threeOfAnyLetter(boxid: CharArray): Boolean {
        return xOfAnyLetter(boxid, 3)
    }

    fun answerPart1(): Int {
        val two = input.filter { twoOfAnyLetter(it) }.count()
        val three = input.filter { threeOfAnyLetter(it) }.count()

        return two * three
    }

    private fun findMatchingChars(first: CharArray, second: CharArray): List<Char> {
        val matching = mutableListOf<Char>()

        for (i in 0 until first.count()) {
            if (first[i] == second[i]) {
                matching.add(first[i])
            }
        }

        return matching
    }

    fun answerPart2(): String {
        for (i in 0 until input.size - 1) {
            for (j in i + 1 until input.size) {
                val matches = findMatchingChars(input[i], input[j])
                if (matches.count() == input[i].count()-1) {
                    return matches.joinToString("")
                }
            }
        }
        return "??"
    }
}

fun main(args: Array<String>) {
    println("Day2 part1: " + Day2.answerPart1())
    println("Day2 part2: " + Day2.answerPart2())
}