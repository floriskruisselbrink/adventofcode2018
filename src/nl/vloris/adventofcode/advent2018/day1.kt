package nl.vloris.adventofcode.advent2018

object Day1 {
    private val input = javaClass.getResource("day1-input.txt")
        .readText()
        .lines()
        .filter { it.isNotEmpty() }
        .map { it.toInt() }

    fun answerPart1(): Int {
        var frequency = 0
        for (it in input) {
            frequency += it
        }

        return frequency
    }

    fun answerPart2(): Int {
        var frequency = 0
        val foundFrequencies = mutableSetOf(frequency)

        while (true) {
            for (it in input) {
                frequency += it
                if (foundFrequencies.contains(frequency)) {
                    return frequency
                }

                foundFrequencies.add(frequency)
            }
        }
    }
}

fun main(args: Array<String>) {
    println("Day1 part1: " + Day1.answerPart1())
    println("Day1 part2: " + Day1.answerPart2())
}