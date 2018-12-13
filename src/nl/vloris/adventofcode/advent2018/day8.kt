package nl.vloris.adventofcode.advent2018

object Day8 {
    private val input = javaClass.getResource("day8-input.txt")
        .readText()
        .split(" ", "\n")
        .filter { it.isNotEmpty() }
        .map { it.toInt() }

    private fun parseNode(iterator: Iterator<Int>): Int {
        val numChilds = iterator.next()
        val numMetadata = iterator.next()

        var runningTotal = 0

        for (i in 0 until numChilds) {
            runningTotal += parseNode(iterator)
        }

        for (i in 0 until numMetadata) {
            val metadata = iterator.next()
            runningTotal += metadata
        }

        return runningTotal
    }

    fun answerPart1(): Int {
        val iterator = input.iterator()

        return parseNode(iterator)
    }
}

fun main(args: Array<String>) {
    println("Day 8, part 1: " + Day8.answerPart1())
}