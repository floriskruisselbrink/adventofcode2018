package nl.vloris.adventofcode.advent2018

object Day8 {
    private val input = javaClass.getResource("day8-input.txt")
        .readText()
        .split(" ", "\n")
        .filter { it.isNotEmpty() }
        .map { it.toInt() }

    private fun parseNode1(iterator: Iterator<Int>): Int {
        val numChilds = iterator.next()
        val numMetadata = iterator.next()

        var runningTotal = 0

        for (i in 0 until numChilds) {
            runningTotal += parseNode1(iterator)
        }

        for (i in 0 until numMetadata) {
            val metadata = iterator.next()
            runningTotal += metadata
        }

        return runningTotal
    }

    fun answerPart1(): Int {
        val iterator = input.iterator()

        return parseNode1(iterator)
    }

    private fun parseNode2(iterator: Iterator<Int>): Int {
        val numChilds = iterator.next()
        val numMetadata = iterator.next()

        val childNodes = mutableListOf<Int>()
        for (i in 0 until numChilds) {
            childNodes.add(parseNode2(iterator))
        }

        var runningTotal = 0
        // 1. no childs? add metadata together
        if (numChilds == 0) {
            for (i in 0 until numMetadata) {
                val metadata = iterator.next()
                runningTotal += metadata
            }
        }
        // 2. childs? metadata are indices to childnodes
        else {
            for (i in 0 until numMetadata) {
                val metadata = iterator.next()
                if (metadata > childNodes.size) continue
                runningTotal += childNodes[metadata-1]
            }
        }

        return runningTotal
    }

    fun answerPart2(): Int {
        val iterator = input.iterator()

        return parseNode2(iterator)
    }
}

fun main(args: Array<String>) {
    println("Day 8, part 1: " + Day8.answerPart1())
    println("Day 8, part 2: " + Day8.answerPart2())
}