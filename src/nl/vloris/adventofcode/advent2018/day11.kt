package nl.vloris.adventofcode.advent2018

class Grid(private val size: Int) : Iterable<Int> {
    private val grid = IntArray(size*size)

    operator fun get(x: Int, y: Int): Int {
        return grid[index(x, y)]
    }

    operator fun set(x: Int, y: Int, value: Int) {
        grid[index(x, y)] = value
    }

    private fun index(x: Int, y: Int): Int {
        return (y-1) * size + (x-1)
    }

    override fun iterator(): Iterator<Int> = grid.iterator()
}

object Day11 {
    private fun calculatePower(x: Int, y: Int, gridId: Int): Int {
        val rackId = x + 10
        var power = rackId * y
        power += gridId
        power *= rackId
        power = power / 100 % 10
        power -= 5

        return power
    }

    private fun createGrid(gridId: Int): Grid {
        val fuelGrid = Grid(300)
        for (x in 1..300) {
            for (y in 1..300) {
                fuelGrid[x, y] = calculatePower(x, y, gridId)
            }
        }

        return fuelGrid
    }

    private fun powerOf3x3(grid: Grid, left: Int, top: Int): Int {
        var runningTotal = 0
        for (x in left..left + 2) {
            for (y in top..top + 2) {
                runningTotal += grid[x, y]
            }
        }
        return runningTotal
    }

    fun answerPart1(): String {
        val fuelGrid = createGrid(7139)

        var maxX = -1
        var maxY = -1
        var maxPower = -1

        for (left in 1..298) {
            for (top in 1..298) {
                val power = powerOf3x3(fuelGrid, left, top)
                if (power > maxPower) {
                    maxPower = power
                    maxX = left
                    maxY = top
                }
            }
        }

        return "$maxX,$maxY"
    }
}

fun main(args: Array<String>) {
    println("Day11, part1: " + Day11.answerPart1())
}