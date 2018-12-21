package nl.vloris.adventofcode.advent2018

import java.lang.StringBuilder

object Day18 {
    class Map(
        val grid: Array<CharArray>
    ) {
        val width: Int = grid[0].size
        val height: Int = grid.size

        fun adjacentTo(x: Int, y: Int): List<Char> {
            return listOfNotNull(
                this[x - 1, y - 1],
                this[x, y - 1],
                this[x + 1, y - 1],
                this[x - 1, y],
                this[x + 1, y],
                this[x - 1, y + 1],
                this[x, y + 1],
                this[x + 1, y + 1]
            )
        }

        operator fun get(x: Int, y: Int): Char? {
            return if (x < 0 || x >= width || y < 0 || y >= height)
                null
            else
                grid[y][x]
        }

        operator fun set(x: Int, y: Int, value: Char) {
            grid[y][x] = value
        }

        override fun toString(): String {
            val sb = StringBuilder()
            for (line in grid) {
                sb.append(line.joinToString(""))
                sb.append("\n")
            }
            return sb.toString()
        }

        companion object {
            fun emptyMap(width: Int, height: Int): Map {
                val grid = Array(height) { CharArray(width) }
                return Map(grid)
            }
        }
    }

    private fun createMap(filename: String): Map {
        val input = javaClass.getResource(filename)
            .readText()
            .lines()
            .filter { it.isNotEmpty() }
            .map { it.toCharArray() }
            .toTypedArray()

        return Map(input)
    }

    private fun newValue(oldValue: Char, adjacentValues: List<Char>): Char {
        val trees = adjacentValues.count { it == '|' }
        val lumberyards = adjacentValues.count { it == '#' }
        return when (oldValue) {
            '.' -> if (trees > 2) '|' else '.'
            '|' -> if (lumberyards > 2) '#' else '|'
            '#' -> if (lumberyards > 0 && trees > 0) '#' else '.'
            else -> throw IllegalArgumentException()
        }
    }

    private fun transformMap(originalMap: Map): Map {
        val newMap = Map.emptyMap(originalMap.width, originalMap.height)

        for (x in 0 until originalMap.width) {
            for (y in 0 until originalMap.height) {
                val value = originalMap[x, y]
                newMap[x, y] = newValue(value!!, originalMap.adjacentTo(x, y))
            }
        }
        return newMap
    }

    fun answerPart1(): Int {
        val startMap = createMap("day18-input.txt")
        println(startMap.toString())

        var map = startMap
        for (minute in 1..10) {
            map = transformMap(map)
        }

        var trees = 0
        var lumberyards = 0
        for (line in map.grid) {
            trees += line.count { it == '|' }
            lumberyards += line.count { it == '#' }
        }

        return trees * lumberyards
    }

}

fun main(args: Array<String>) {
    println("Day18, part1: " + Day18.answerPart1())
}