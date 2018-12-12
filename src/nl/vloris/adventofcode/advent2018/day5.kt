package nl.vloris.adventofcode.advent2018

object Day5 {
    private val input = javaClass.getResource("day5-input.txt")
        .readText().trim()

    private val regex =
        Regex("(aA|bB|cC|dD|eE|fF|gG|hH|iI|jJ|kK|lL|mM|nN|oO|pP|qQ|rR|sS|tT|uU|vV|wW|xX|yY|zZ|Aa|Bb|Cc|Dd|Ee|Ff|Gg|Hh|Ii|Jj|Kk|Ll|Mm|Nn|Oo|Pp|Qq|Rr|Ss|Tt|Uu|Vv|Ww|Xx|Yy|Zz)")

    fun answerPart1(): Int {
        var result = input

        while (true) {
            val replacement = regex.replaceFirst(result, "")
            if (replacement == result) {
                break
            }
            else {
                result = replacement
            }
        }

        return result.length
    }
}

fun main(args: Array<String>) {
    println("Day5 part1: " + Day5.answerPart1())
}