package nl.vloris.adventofcode.advent2018

object Day5 {
    private val input = javaClass.getResource("day5-input.txt")
        .readText().trim()

    private val fullRegex =
        Regex("(aA|bB|cC|dD|eE|fF|gG|hH|iI|jJ|kK|lL|mM|nN|oO|pP|qQ|rR|sS|tT|uU|vV|wW|xX|yY|zZ|Aa|Bb|Cc|Dd|Ee|Ff|Gg|Hh|Ii|Jj|Kk|Ll|Mm|Nn|Oo|Pp|Qq|Rr|Ss|Tt|Uu|Vv|Ww|Xx|Yy|Zz)")

    private fun reactPolymer(polymer: String, toRemove: Regex): String {
        var result = polymer

        while (true) {
            val replacement = toRemove.replace(result, "")
            if (replacement == result) {
                break
            } else {
                result = replacement
            }
        }
        return result
    }

    fun answerPart1(): Int {
        val result = reactPolymer(input, fullRegex)
        return result.length
    }

    fun answerPart2(): Int {
        var shortestPolymer = Int.MAX_VALUE

        for (lowercase in 'a'..'z') {
            val uppercase = lowercase.toUpperCase()
            val result1 = reactPolymer(input, "$lowercase|$uppercase".toRegex())
            val result2 = reactPolymer(result1, fullRegex)
            if (result2.length < shortestPolymer) {
                shortestPolymer = result2.length
            }
        }

        return shortestPolymer
    }
}

fun main(args: Array<String>) {
    println("Day5 part1: " + Day5.answerPart1())
    println("Day5 part2: " + Day5.answerPart2())
}