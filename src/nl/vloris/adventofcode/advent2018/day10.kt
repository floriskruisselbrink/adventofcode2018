package nl.vloris.adventofcode.advent2018

data class Point(val posX: Int, val posY: Int, val velocityX: Int, val velocityY: Int)

object Day10 {
    private val input = javaClass.getResource("day10-input.txt")
        .readText()
        .lines()
        .filter { it.isNotEmpty() }
        .map { line ->
            val args = line
                .split("position=<", ", ", "> velocity=<", ", ", ">")
                .drop(1)
                .dropLast(1)
                .map { it.trim().toInt() }
            Point(args[0], args[1], args[2], args[3])
        }

    private fun isIntersting(points: List<Point>): Boolean {
        val minY = points.map { it.posY }.min()!!
        val maxY = points.map { it.posY }.max()!!

        return (maxY-minY) < 15
    }

    private fun visualize(points: List<Point>) {
        val minX = points.map { it.posX }.min()!!
        val minY = points.map { it.posY }.min()!!
        val maxX = points.map { it.posX }.max()!!
        val maxY = points.map { it.posY }.max()!!

        val offsetX = -1 * minX
        val width = maxX - minX + 1

        for (y in minY..maxY) {
            val line = CharArray(width) { ' ' }
            points.filter { it.posY == y }.forEach {
                line[it.posX + offsetX] = '#'
            }

            println(line.joinToString(""))
        }
    }

    private fun translatePoints(points: List<Point>): List<Point> {
        return points.map { point ->
            Point(
                point.posX + point.velocityX,
                point.posY + point.velocityY,
                point.velocityX,
                point.velocityY
            )
        }
    }

    fun answerPart1(): Int {
        var iteration = 0
        var points = input
        do {
            points = translatePoints(points)
            iteration++
        } while (!isIntersting(points))

        visualize(points)
        return iteration
    }
}

fun main(args: Array<String>) {
    println("Day10, part1: ")
    val iteration = Day10.answerPart1()
    println("Day10, part2: $iteration")
}