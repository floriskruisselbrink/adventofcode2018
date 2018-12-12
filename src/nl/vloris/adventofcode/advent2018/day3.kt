package nl.vloris.adventofcode.advent2018

object Day3 {
    private val input = javaClass.getResource("day3-input.txt")
        .readText()
        .lines()
        .filter { it.isNotEmpty() }
        .map { claim ->
            val (id, left, top, width, height) = claim.split(" @ ", ",", ": ", "x")
                .filter { it.isNotEmpty() }

            Claim(id, left.toInt(), top.toInt(), width.toInt(), height.toInt())
        }

    data class Claim(val id: String, val left: Int, val top: Int, val width: Int, val height: Int) {
        val right = left + width
        val bottom = top + height
        var overlap = 0
    }

    private fun index(x: Int, y: Int): Int {
        return x + y * 1000
    }

    fun answerPart1(): Int {
        val cloth = Array(1000 * 1000) { 0 }

        for (claim in input) {
            for (x in claim.left until claim.right) {
                for (y in claim.top until claim.bottom) {
                    cloth[index(x, y)]++
                }
            }
        }

        return cloth.filter { it > 1 }.count()
    }

    fun answerPart2(): String? {
        val cloth = arrayOfNulls<Claim>(1000 * 1000)

        for (claim in input) {
            claim.overlap = 0

            for (x in claim.left until claim.right) {
                for (y in claim.top until claim.bottom) {
                    val i = index(x, y)
                    val existingClaim = cloth[i]

                    if (existingClaim == null) {
                        cloth[i] = claim
                    }
                    else {
                        claim.overlap++
                        existingClaim.overlap++
                    }
                }
            }
        }

        return input.firstOrNull { it.overlap == 0 }?.id
    }
}

fun main(args: Array<String>) {
    println("Day3 part 1: " + Day3.answerPart1())
    println("Day3 part 2: " + Day3.answerPart2())
}