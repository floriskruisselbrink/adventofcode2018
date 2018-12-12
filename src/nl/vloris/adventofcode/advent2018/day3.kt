package nl.vloris.adventofcode.advent2018

object Day3 {
    private val input = javaClass.getResource("day3-input.txt")
        .readText()
        .lines()
        .filter { it.isNotEmpty() }

    private fun index(x: Int, y: Int): Int {
        return x + y * 1000
    }

    private fun parseClaim(claim: String, cloth: Array<Int>) {
        val (_, left, top, width, height) = claim.split("#", " @ ", ",", ": ", "x")
            .filter { it.isNotEmpty() }
            .map { it.toInt() }

        for (x in left until left + width) {
            for (y in top until top + height) {
                cloth[index(x, y)]++
            }
        }
    }

    fun answerPart1(): Int {
        val cloth = Array(1000 * 1000) { 0 }

        for (claim in input) {
            parseClaim(claim, cloth)
        }

        return cloth.filter { it > 1 }.count()
    }
}

fun main(args: Array<String>) {
    println("Day3 part 1: " + Day3.answerPart1())
}